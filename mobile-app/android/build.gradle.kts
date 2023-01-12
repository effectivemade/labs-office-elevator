plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
}

android {
    compileSdk = ConfigData.Android.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.Android.minSdkVersion
        targetSdk = ConfigData.Android.targetSdkVersion
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = ConfigData.Android.kotlinCompilerExtensionVersion
    }
    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
        }
    }
    lintOptions {
        isCheckReleaseBuilds = false
    }

    packagingOptions {
        exclude("META-INF/*")
    }
    signingConfigs {
        getByName("debug") {
//            keyAlias = "debug"
            keyPassword = "android"
            storeFile = file("${rootDir}/keystore/debug.keystore")
            storePassword = "android"
        }
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(Dependencies.AndroidX.AppCompat.appCompat)
    implementation(Dependencies.Android.material)

    implementation(Dependencies.JetBrains.Compose.runtime)
    implementation(Dependencies.JetBrains.Compose.ui)
    implementation(Dependencies.JetBrains.Compose.foundationLayout)
    implementation(Dependencies.JetBrains.Compose.material)
    //implementation(Dependencies.AndroidX.Activity.activityCompose)
}