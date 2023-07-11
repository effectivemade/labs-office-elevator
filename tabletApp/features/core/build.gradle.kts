plugins {
    id(Plugins.AndroidLib.plugin)
    id(Plugins.Kotlin.plugin)
}

android {
   compileSdk = 33
}
dependencies {
    implementation("androidx.slice:slice-view:1.0.0")
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
            }
        }
    }
}
