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
                implementation(project(Modules.OdysseyCore))
                implementation(Dependencies.Utils.UUID)
            }
        }
        named("androidMain") {
            dependencies {
                implementation(Dependencies.JetBrains.Kotlin.coroutinesAndroid)
                implementation(Dependencies.AndroidX.Activity.activityCompose)
            }
        }
    }
}