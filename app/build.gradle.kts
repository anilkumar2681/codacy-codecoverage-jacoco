plugins {
    id("com.android.application")
    kotlin("android")
}
rootProject.apply {
    rootProject.file("jacoco-report.gradle.kts")
}

jacoco{
    toolVersion = "0.8.7"
}

android {
    compileSdk = (ConfigData.compileSdkVersion)
    buildToolsVersion = (ConfigData.buildToolsVersion)

    defaultConfig {
        applicationId = "com.team07.demokotlindsl"
        minSdk = (ConfigData.minSdkVersion)
        targetSdk = (ConfigData.targetSdkVersion)
        versionCode = (ConfigData.versionCode)
        versionName = (ConfigData.versionName)

        testInstrumentationRunner  = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug"){
            isTestCoverageEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions{
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }
}
dependencies {

    implementation(Deps.coreKTX)
    implementation(Deps.appCompat)
    implementation(Deps.materialDesign)
    implementation(Deps.constraintLayout)
    testImplementation(Deps.junit)
}