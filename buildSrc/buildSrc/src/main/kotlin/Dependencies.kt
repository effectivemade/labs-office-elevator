object Dependencies {

    object Versions {
        const val activityKtx = "1.7.2"
        const val kotlin = "1.8.20"
        const val agp = "7.4.2"
        const val composeMultiplatform = "1.4.0"
        const val compose = "1.4.6"
        const val androidxAppCompat = "1.6.1"
        const val androidxActivityCompose = "1.7.2"
        const val composeUiTooling = "1.4.3"
        const val libres = "1.1.8"
        const val composeImageLoader = "1.3.1"
        const val napier = "2.6.1"
        const val buildConfig = "3.1.0"
        const val kotlinxCoroutines = "1.7.1"
        const val ktor = "2.3.1"
        const val composeIcons = "1.1.0"
        const val kotlinxSerialization = "1.5.1"
        const val kotlinxDatetime = "0.4.0"
        const val googleAuth = "20.5.0"
        const val decompose = "2.0.0-compose-experimental-beta-01"
        const val mviKotlin = "3.2.1"
        const val koin = "3.4.0"
        const val essenty = "1.1.0"
        const val logback = "1.2.11"
        const val shadow = "7.1.2"
        const val tvCompose = "1.0.0-alpha04"
        const val retrofit = "2.9.0"
        const val moshi = "1.14.0"
        const val hilt = "2.44"
        const val hiltNav = "1.0.0"
        const val navigationCompose = "2.5.3"
        const val okhttp3 = "4.10.0"
        const val retrofitMoshi = "2.9.0"
        const val coil = "2.2.2"
        const val coilSvg = "2.3.0"
        const val zxing = "3.5.1" // QR
        const val notionSDK = "1.8.0"

    }
    object Coil {
        const val coil =
            "io.coil-kt:coil-compose:${Versions.coil}"
        const val svgDecoder =
            "io.coil-kt:coil-svg:${Versions.coilSvg}"
    }

    object NotionSDK {
        const val notion =
            "com.github.seratch:notion-sdk-jvm-core:${Versions.notionSDK}"
    }

    object QRCodeGenerator {
        const val zxing =
            "com.google.zxing:core:${Versions.zxing}"
    }
    object Retrofit {
        const val retrofit =
            "com.squareup.retrofit2:retrofit:${Versions.retrofit}"

        const val moshiConverter =
            "com.squareup.retrofit2:converter-moshi:${Versions.retrofitMoshi}"

    }

    object Okhttp {
        const val okhttp =
            "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"

        const val okhttpLoggingIntersepter =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
    }

    object Moshi {
        const val moshi =
            "com.squareup.moshi:moshi:${Versions.moshi}"

        const val moshiAdapter =
            "com.squareup.moshi:moshi-adapters:${Versions.moshi}"

        const val moshiCodegen =
            "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    }

    object Hilt {
        const val hilt =
            "com.google.dagger:hilt-android:${Versions.hilt}"

        const val hiltNav =
            "androidx.hilt:hilt-navigation-compose:${Versions.hiltNav}"

        const val hiltCompiller =
            "com.google.dagger:hilt-compiler:${Versions.hilt}"
    }

    object TvDeps {
        const val tvFoundation =
            "androidx.tv:tv-foundation:${Versions.tvCompose}"

        const val tvMaterial =
            "androidx.tv:tv-material:${Versions.tvCompose}"
    }

    object ComposeNavigation {
        const val navigation =
            "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    }
    object Ktor {
        const val ktorVersion = "2.3.1"

        object Client {
            const val Core =
                "io.ktor:ktor-client-core:$ktorVersion"
            const val CommonLogging =
                "io.ktor:ktor-client-logging:$ktorVersion"
            const val CIO = "io.ktor:ktor-client-cio:$ktorVersion"
            const val Android = "io.ktor:ktor-client-android:${Versions.ktor}"
            const val Darwin = "io.ktor:ktor-client-darwin:${Versions.ktor}"
        }

        object Server {
            const val Netty = "io.ktor:ktor-server-netty-jvm:$ktorVersion"
            const val Logback = "ch.qos.logback:logback-classic:1.2.11"
        }
    }


    object Google {
        private const val VERSION = "20.4.0"
        const val SignIn = "com.google.android.gms:play-services-auth:$VERSION"
        const val ApiClient = "com.google.api-client:google-api-client:2.1.1"
    }


    object AndroidX {
        const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
        const val activityCompose =
            "androidx.activity:activity-compose:${Versions.androidxActivityCompose}"
    }

    object Compose {
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeUiTooling}"
        const val material = "androidx.compose.material:material:${Versions.composeMultiplatform}"
    }

    object Libres {
        const val libresCompose = "io.github.skeptick.libres:libres-compose:${Versions.libres}"
    }

    object ImageLoader {
        const val imageLoader = "io.github.qdsfdhvh:image-loader:${Versions.composeImageLoader}"
    }

    object Napier {
        const val napier = "io.github.aakira:napier:${Versions.napier}"
    }

    object KotlinxCoroutines {
        const val core =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinxCoroutines}"
    }

    object ComposeIcons {
        const val featherIcons = "br.com.devsrsouza.compose.icons:feather:${Versions.composeIcons}"
    }

    object KotlinxSerialization {
        const val json =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
    }

    object KotlinxDatetime {
        const val kotlinxDatetime =
            "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDatetime}"
    }

    object GoogleAuth {
        const val googleAuth = "com.google.android.gms:play-services-auth:${Versions.googleAuth}"
    }

    object Decompose {
        const val decompose = "com.arkivanov.decompose:decompose:${Versions.decompose}"
        const val extensions =
            "com.arkivanov.decompose:extensions-compose-jetbrains:${Versions.decompose}"
    }

    object MviKotlin {
        const val mviKotlin = "com.arkivanov.mvikotlin:mvikotlin:${Versions.mviKotlin}"
        const val mviKotlinMain = "com.arkivanov.mvikotlin:mvikotlin-main:${Versions.mviKotlin}"
        const val mviKotlinExtensionsCoroutines =
            "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${Versions.mviKotlin}"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
    }

    object Essenty {
        const val essenty = "com.arkivanov.essenty:lifecycle:${Versions.essenty}"
    }
}