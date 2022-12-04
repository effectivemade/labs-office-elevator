import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
    id("com.codingfeline.buildkonfig")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.materialIconsExtended)
                implementation(project(Modules.OdysseyCompose))
                implementation(project(Modules.OdysseyCore))
                implementation(Dependencies.Ktor.Client.Core)
                implementation(Dependencies.Ktor.Client.commonLogging)
                implementation(Dependencies.Ktor.Client.cio)
                implementation(Dependencies.Ktor.Client.websockets)
                implementation(Dependencies.Coroutines.Core)
                implementation(Dependencies.Cryptohash)
                implementation("com.soywiz.korlibs.krypto:krypto:2.7.0")
            }

            val androidMain by getting {
                dependencies {
                    implementation(Dependencies.AndroidX.AppCompat.fragmentKtx)
                    implementation(Dependencies.Ktor.Client.androidOKHttp)
                    implementation(Dependencies.Coroutines.Android)
                }
            }
        }
    }
}
buildkonfig {
    packageName = "band.effective.office.elevator.common.compose"

    defaultConfigs {
        buildConfigField(STRING, "baseUrl", "0.0.0.0")
    }
}