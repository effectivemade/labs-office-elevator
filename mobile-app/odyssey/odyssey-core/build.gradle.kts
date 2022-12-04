plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
}

group = ConfigData.group
version = ConfigData.versionName

kotlin {

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(Dependencies.JetBrains.Kotlin.coroutines)
            }
        }
        named("commonTest")

        named("androidMain") {
            dependencies {
                implementation(Dependencies.AndroidX.Activity.activityCompose)
            }
        }
    }
}