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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = ConfigData.Android.kotlinCompilerExtensionVersion
    }
    buildFeatures {
        compose = true
    }

    signingConfigs {
        getByName("debug") {
            keyPassword = "android"
            storeFile = file("${rootDir}/mobile/keystore/debug.keystore")
            storePassword = "android"
        }
        create("release") {
            keyAlias = System.getenv()["OFFICE_ELEVATOR_RELEASE_ALIAS"]
            keyPassword = System.getenv()["OFFICE_ELEVATOR_RELEASE_KEY_PASSWORD"]
            storeFile = file("${rootDir}/mobile/keystore/main.keystore")
            storePassword = System.getenv()["OFFICE_ELEVATOR_RELEASE_STORE_PASSWORD"]
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isMinifyEnabled = true
        }
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
}

dependencies {
    implementation(project(":mobile:shared"))

    implementation(Dependencies.AndroidX.AppCompat.appCompat)
    implementation(Dependencies.Android.material)
    implementation(Dependencies.AndroidX.Activity.activityCompose)

    implementation(Dependencies.JetBrains.Compose.runtime)
    implementation(Dependencies.JetBrains.Compose.ui)
    implementation(Dependencies.JetBrains.Compose.foundationLayout)
    implementation(Dependencies.JetBrains.Compose.material)
}