plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("org.jetbrains.compose")
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

    lintOptions {
        isCheckReleaseBuilds = false
    }

    packagingOptions {
        exclude("META-INF/*")
    }
}

dependencies {
    implementation(project(Modules.CommonUI))
    implementation(project(Modules.OdysseyCompose))
    implementation(project(Modules.OdysseyCore))
    implementation(compose.material)

    implementation(Dependencies.AndroidX.AppCompat.appCompat)
    implementation(Dependencies.AndroidX.Activity.activityCompose)
    implementation(Dependencies.Images.kamel)
}