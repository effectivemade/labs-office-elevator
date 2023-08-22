plugins {
    id(Plugins.Android.plugin)
    id(Plugins.MultiplatformCompose.plugin)
    id(Plugins.Kotlin.plugin)
    id(Plugins.Parcelize.plugin)
    id(Plugins.Libres.plugin)
    id(Plugins.GoogleServices.plugin)
}

android {
    namespace = "band.effective.office.tablet"
    compileSdk = 33

    defaultConfig {
        applicationId = "band.effective.office.tablet"
        versionCode = 2
        versionName = "0.1.0"

        minSdk = 26
        targetSdk = 33
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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

                // Decompose
                implementation(Dependencies.Decompose.decompose)
                implementation(Dependencies.Decompose.extensions)

                //Libres
                implementation(Dependencies.Libres.libresCompose)

                // MVI Kotlin
                api(Dependencies.MviKotlin.mviKotlin)
                api(Dependencies.MviKotlin.mviKotlinMain)
                api(Dependencies.MviKotlin.mviKotlinExtensionsCoroutines)

                implementation(project(":tabletApp:features:selectRoom"))
                implementation(project(":tabletApp:features:roomInfo"))
                implementation(project(":tabletApp:features:freeNegotiationsScreen"))
                implementation(project(":tabletApp:features:core"))

            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.AndroidX.appCompat)
                implementation(Dependencies.AndroidX.activityCompose)
                implementation(Dependencies.Compose.uiTooling)
                implementation("com.google.firebase:firebase-messaging-ktx:23.1.0")
            }
        }
    }
}
