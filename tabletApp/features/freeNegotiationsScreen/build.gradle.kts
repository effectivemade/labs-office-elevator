plugins {
    id(Plugins.AndroidLib.plugin)
    id(Plugins.MultiplatformCompose.plugin)
    id(Plugins.Kotlin.plugin)
    id(Plugins.Parcelize.plugin)
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
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)

                // Decompose
                implementation(Dependencies.Decompose.decompose)
                implementation(Dependencies.Decompose.extensions)
            }
        }
    }
}
