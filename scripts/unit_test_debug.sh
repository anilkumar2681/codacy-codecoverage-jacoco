#!/bin/bash
# Exit on error
set -e
git fetch --unshallow
git fetch origin
# Get all the modules that were changed
while read line; do
  module_name=${line%%/*}
  # add the modules only 1 time
  if grep  -q -v -E "(^| )${module_name}( |$)" <<< "${MODULES}";then
    MODULES="${MODULES} ${module_name}"
  fi
done < <(git diff --name-only origin/$GITHUB_BASE_REF)
changed_modules=$MODULES
echo $changed_modules
# Get a list of all available gradle tasks
# need to add gradle param maven_publish_plugin.publishMonorepoLib to work with Monorepo
AVAILABLE_TASKS=$(./gradlew tasks --all -Pmaven_publish_plugin.publishMonorepoLib)

# Check if these modules have gradle tasks
build_commands=""
for module in $changed_modules
do
  if [[ $AVAILABLE_TASKS =~ $module":" && $module != "app" ]]; then
    build_commands=${build_commands}" :"${module}":testDebugUnitTest"
  fi
done
echo $build_commands
eval "./gradlew ${build_commands}"