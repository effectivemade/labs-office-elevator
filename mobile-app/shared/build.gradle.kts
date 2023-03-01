import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.*
import java.io.*

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
        buildConfigField(STRING, "webClient", getLocalProperty("webClient"))
        buildConfigField(STRING, "iosClient", getLocalProperty("iosClient"))
        buildConfigField(STRING, "androidClient", getLocalProperty("androidClient"))
    }
}

fun Project.getLocalProperty(key: String): String? {
    val file = "${rootProject.rootDir.path.substringBefore("mobile-app")}keystore/elevator.properties"
    val properties = Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8)
            .use { reader ->
            properties.load(reader)
        }
    } else error("File from not found")

    return properties.getProperty(key).toString()
}