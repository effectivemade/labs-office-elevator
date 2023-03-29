import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
}

val keystorePropertiesFile = rootProject.file("keystore/keystore.properties")

val keystoreProperties = Properties()

keystoreProperties.load(FileInputStream(keystorePropertiesFile))

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
            storeFile = file("${rootDir}/signing/debug.keystore")
            storePassword = "android"
        }
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(rootDir.path.plus("/").plus(keystoreProperties["storeFile"] as String))
            storePassword = keystoreProperties["storePassword"] as String
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

    implementation(Dependencies.JetBrains.Compose.runtime)
    implementation(Dependencies.JetBrains.Compose.ui)
    implementation(Dependencies.JetBrains.Compose.foundationLayout)
    implementation(Dependencies.JetBrains.Compose.material)
}