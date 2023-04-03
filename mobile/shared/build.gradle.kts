import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("android-setup")
    id("org.jetbrains.compose") version Dependencies.JetBrains.Compose.VERSION
    id("com.codingfeline.buildkonfig")
}

version = "0.0.1"

kotlin {
    android()
    ios()

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

                api(Dependencies.Napier.logger)

                api(Dependencies.precompose)

                implementation(Dependencies.KViewmodel.core)
                implementation(Dependencies.KViewmodel.composeExt)
                implementation(Dependencies.KViewmodel.odyssey)
                api(Dependencies.asyncImageCompose)
            }
        }

        val androidMain by getting {
            dependencies {
                //implementation("androidx.appcompat:appcompat:1.4.0-alpha03")
                //implementation("androidx.core:core-ktx:1.6.0")
                implementation(Dependencies.Google.SignIn)
                implementation(Dependencies.AndroidX.Activity.activityCompose)
            }
        }
        //val iosX64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            // iosX64Main.dependsOn(this)
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

buildkonfig {
    packageName = "band.effective.office.elevator"

    defaultConfigs {
        buildConfigField(
            STRING,
            "webClient",
            "726357293621-s4lju93oibotmefghoh3b3ucckalh933.apps.googleusercontent.com"
        )
    }
}