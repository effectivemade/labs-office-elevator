import dev.icerock.gradle.MRVisibility.Public

plugins {
    id(Plugins.Kotlin.plugin)
    id(Plugins.MultiplatformCompose.plugin)
    id(Plugins.CocoaPods.plugin)
    id(Plugins.Android.plugin)
    id(Plugins.SQLDelight.plugin) version Plugins.SQLDelight.version
    id(Plugins.BuildConfig.plugin)
    id(Plugins.Serialization.plugin)
    id(Plugins.Parcelize.plugin)
    id(Plugins.Moko.plugin)
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

            export(Dependencies.Decompose.decompose)
            export(Dependencies.Essenty.essenty)
        }
        pod("GoogleSignIn") {}
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(Dependencies.ImageLoader.imageLoader)
                implementation(Dependencies.Napier.napier)
                implementation(Dependencies.KotlinxCoroutines.core)
                api(Dependencies.Ktor.Client.Core)
                api(Dependencies.Ktor.Client.CommonLogging)
                implementation(Dependencies.ComposeIcons.featherIcons)
                implementation(Dependencies.KotlinxSerialization.json)
                implementation(Dependencies.KotlinxDatetime.kotlinxDatetime)

                // MVI Kotlin
                api(Dependencies.MviKotlin.mviKotlin)
                api(Dependencies.MviKotlin.mviKotlinMain)
                api(Dependencies.MviKotlin.mviKotlinExtensionsCoroutines)

                // Decompose
                api(Dependencies.Decompose.decompose)
                api(Dependencies.Decompose.extensions)

                // Koin
                api(Dependencies.Koin.core)

                api(Dependencies.Essenty.essenty)

                //Moko
                api(Dependencies.Moko.resourcesCompose)

                implementation(Dependencies.Calendar.composeDatePicker)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("dev.icerock.moko:resources-test:0.23.0")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Dependencies.AndroidX.appCompat)
                implementation(Dependencies.AndroidX.activityCompose)
                implementation(Dependencies.Compose.uiTooling)
                implementation(Dependencies.KotlinxCoroutines.android)
                api(Dependencies.Ktor.Client.Android)
                implementation(Dependencies.Google.SignIn)
                implementation(Dependencies.AndroidX.activityKtx)
                implementation(Dependencies.SqlDelight.androidDriver)
                // Koin
                api(Dependencies.Koin.android)

                api(Dependencies.Ktor.Server.Logback)
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
                implementation(Dependencies.Ktor.Client.Darwin)
                implementation(files("iosApp/GoogleAuthorization/GoogleAuthorization/Sources"))
                implementation(Dependencies.SqlDelight.nativeDriver)
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

multiplatformResources {
    multiplatformResourcesPackage = "band.effective.office.elevator"
    multiplatformResourcesVisibility = Public
    multiplatformResourcesClassName = "MainRes"
    iosBaseLocalizationRegion = "ru" // optional, default "en"
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

sqldelight {
    databases {
        create("Database") {
            packageName.set("band.effective.office.elevator")
        }
    }
}