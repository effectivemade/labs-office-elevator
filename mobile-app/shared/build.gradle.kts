plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    //id("com.android.library")
    id("android-setup")
    id("org.jetbrains.compose") version Dependencies.JetBrains.Compose.VERSION
}

version = "0.0.1"

kotlin {
    android()
    ios()
    //iosSimulatorArm64()

    cocoapods {
        summary = "Multiplatform Compose Shared Module"
        homepage = "https://github.com/Radch-enko"
        podfile = project.file("../iosApp/Podfile")
        ios.deploymentTarget = "10.0"
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                //implementation(compose.materialIconsExtended)

                implementation(Dependencies.Ktor.Client.Core)
                implementation(Dependencies.Ktor.Client.commonLogging)
                implementation(Dependencies.Ktor.Client.cio)

                implementation(Dependencies.Coroutines.Core)

                implementation(Dependencies.JetBrains.Kotlin.dateTime)

                implementation(Dependencies.Napier.logger)

                api(Dependencies.precompose)
            }
        }

        val androidMain by getting {
            dependencies {
                //implementation("androidx.appcompat:appcompat:1.4.0-alpha03")
                //implementation("androidx.core:core-ktx:1.6.0")
            }
        }
        //val uikitX64Main by getting
        //val uikitArm64Main by getting
        //val iosSimulatorArm64Main by getting
        val iosMain by getting {
            //dependsOn(commonMain)
            //uikitX64Main.dependsOn(this)
            //uikitArm64Main.dependsOn(this)
            //iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Dependencies.Ktor.Client.ios)
            }
        }
    }
}

kotlin {
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.all {
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
        }
    }
}


/*buildkonfig {
    packageName = "band.effective.office.elevator.common.compose"

    defaultConfigs {
        buildConfigField(STRING, "baseUrl", "0.0.0.0")
    }
}*/

/*compose.experimental {
    uikit.application {
        bundleIdPrefix = "band.effective.office.elevator."
        projectName = "OfficeElevator"
        deployConfigurations {
            // Usage ./gradlew uikit:iosDeployIPhone13Debug
            simulator("IPhone13") {
                device = IOSDevices.IPHONE_13_PRO
            }
            // Usage ./gradlew uikit:iosDeployIPhone13ProMaxDebug
            simulator("IPhone13ProMax") {
                device = IOSDevices.IPHONE_13_PRO_MAX
            }
        }
    }
}/*