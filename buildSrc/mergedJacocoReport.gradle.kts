plugins {
    jacoco
}

private boolean isAndroidModule(Project project) {
    boolean isAndroidLibrary = project . plugins . hasPlugin ('com.android.library')
    boolean isAndroidApp = project . plugins . hasPlugin ('com.android.application')
    return isAndroidLibrary || isAndroidApp
}


Jacoco.afterEvaluate { project ->
    if (isAndroidModule(project)) setupAndroidReporting()
    else setupKotlinReporting()
}

fun setupKotlinReporting(){

}


tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
    }
}

task<JacocoReport>("mergedJacocoReport") {

    dependsOn("testDebugUnitTest", "createDebugCoverageReport")

    reports {
        xml.isEnabled = true
    }

    val fileFilter = mutableSetOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*"
    )
    val debugTree = fileTree("${project.buildDir}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }
    val mainSrc = "${project.projectDir}/src/main/kotlin"

    sourceDirectories = files(mainSrc)
    classDirectories = files(debugTree)
    executionData = fileTree(project.buildDir) {
        include(
            "jacoco/testDebugUnitTest.exec",
            "outputs/code_coverage/debugAndroidTest/connected/*coverage.ec"
        )
    }
}
