plugins {
    id(Plugins.Android.plugin)
    id(Plugins.MultiplatformCompose.plugin)
    id(Plugins.Kotlin.plugin)
}

android {
    namespace = "band.effective.office.tablet"
    compileSdk = 33

    defaultConfig {
        applicationId = "band.effective.office.tablet"
        versionCode = 1
        versionName = "0.0.1"

        minSdk = 26
        targetSdk = 33
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        res.srcDir("build/generated/libres/android/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources.excludes.add("META-INF/**")
    }

}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.AndroidX.appCompat)
                implementation(Dependencies.AndroidX.activityCompose)
                implementation(Dependencies.Compose.uiTooling)
            }
        }
    }
}
