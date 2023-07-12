import Dependencies.Versions.compose

plugins {
    id(Plugins.MultiplatformCompose.plugin)
    id(Plugins.AndroidLib.plugin)
    id(Plugins.Kotlin.plugin)
}

android {
   compileSdk = 33
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
                implementation(compose.material)
            }
        }
    }
}
