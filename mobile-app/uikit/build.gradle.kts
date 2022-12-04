import org.jetbrains.compose.experimental.dsl.IOSDevices
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("multiplatform")// version "1.6.21"
    id("org.jetbrains.compose")// version "1.2.0-alpha01-dev686"
}

kotlin {
    iosX64("uikitX64") {
        binaries {
            executable() {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    }
    iosArm64("uikitArm64") {
        binaries {
            executable() {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
                // TODO: the current compose binary surprises LLVM, so disable checks for now.
                freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.CommonUI))
                implementation(project(Modules.OdysseyCore))
                implementation(project(Modules.OdysseyCompose))
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
        }
        val uikitX64Main by getting {
            dependsOn(iosMain)
        }
        val uikitArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

compose.experimental {
    uikit.application {
        bundleIdPrefix = "ru.alexgladkov"
        projectName = "OdysseyiOS"
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
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

kotlin {
    targets.withType<KotlinNativeTarget> {
        binaries.all {
            // TODO: the current compose binary surprises LLVM, so disable checks for now.
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
        }
    }
}