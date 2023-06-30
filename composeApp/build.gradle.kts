@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.kotlin.get().pluginId)
    id(libs.plugins.multiplatformCompose.get().pluginId)
    id(libs.plugins.cocoaPods.get().pluginId)
    id(libs.plugins.android.get().pluginId)
    id(libs.plugins.libres.get().pluginId)
    id(libs.plugins.buildConfig.get().pluginId)
    id(libs.plugins.serialization.get().pluginId)
    id(libs.plugins.parcelize.get().pluginId)
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Compose application framework"
        homepage = "https://github.com/Radch-enko"
        ios.deploymentTarget = "11.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true

            export(libs.bundles.decompose)
        }
        pod("GoogleSignIn") {}
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(libs.libres.libresCompose)
                implementation(libs.imageLoader)
                implementation(libs.napier)
                implementation(libs.kotlinx.coroutines.core)
                api(libs.ktor.client.core)
                api(libs.ktor.client.commonLogging)
                implementation(libs.compose.icons.featherIcons)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)

                // MVI Kotlin
                api(libs.bundles.mviKotlin)

                // Decompose
                api(libs.bundles.decompose)

                // Koin
                api(libs.koin.core)

                api(libs.essenty.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                //android
                implementation(libs.bundles.android.core)
                implementation(libs.google.signin)

                // Koin
                api(libs.koin.android)

                //ktor
                api(libs.ktor.client.android)
                api(libs.ktor.server.logback)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.drawin)
                implementation(files("iosApp/GoogleAuthorization/GoogleAuthorization/Sources"))
            }
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "band.effective.office.elevator"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        applicationId = "band.effective.office.elevator.android"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        res.srcDir("build/generated/libres/android/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources.excludes.add("META-INF/**")
    }

    signingConfigs {
        getByName("debug") {
            keyPassword = "android"
            storeFile = file("${rootDir}/keystore/debug.keystore")
            storePassword = "android"
        }
        create("release") {
            keyAlias = System.getenv()["OFFICE_ELEVATOR_RELEASE_ALIAS"]
            keyPassword = System.getenv()["OFFICE_ELEVATOR_RELEASE_KEY_PASSWORD"]
            storeFile = file("${rootDir}/keystore/main.keystore")
            storePassword = System.getenv()["OFFICE_ELEVATOR_RELEASE_STORE_PASSWORD"]
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isMinifyEnabled = true
        }
    }
}

libres {
    // https://github.com/Skeptick/libres#setup
    generatedClassName = "MainRes" // "Res" by default
    generateNamedArguments = true // false by default
    baseLocaleLanguageCode = "ru" // "en" by default
    camelCaseNamesForAppleFramework = true // false by default

}

buildConfig {
    className("OfficeElevatorConfig")
    packageName("band.effective.office.elevator")
    useKotlinOutput()
    useKotlinOutput { internalVisibility = true }

    buildConfigField(
        "String",
        "webClient", "\"726357293621-s4lju93oibotmefghoh3b3ucckalh933.apps.googleusercontent.com\""
    )
    buildConfigField(
        "String",
        "iosClient",
        "\"726357293621-hegk0410bsb1a5hvl3ihpc4d2bfkmlgb.apps.googleusercontent.com\""
    )
}
