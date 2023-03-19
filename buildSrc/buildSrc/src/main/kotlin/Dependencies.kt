object Dependencies {

    object DI {
        const val kodein = "org.kodein.di:kodein-di:7.1.0"
    }

    object Images {
        const val kamel = "com.alialbaali.kamel:kamel-image:0.3.0"
    }

    object JetBrains {
        object Kotlin {
            // __KOTLIN_COMPOSE_VERSION__
            private const val VERSION = "1.8.0"
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"
            const val testCommon = "org.jetbrains.kotlin:kotlin-test-common:$VERSION"
            const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:$VERSION"
            const val testAnnotationsCommon =
                "org.jetbrains.kotlin:kotlin-test-annotations-common:$VERSION"

            const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"

            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.0"
            const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:1.5.31"

            private const val coroutinesVersion = "1.6.4"
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            const val coroutinesAndroid =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
            const val coroutinesSwing =
                "org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutinesVersion"
            const val coroutinesCommon =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion"
        }

        object Compose {
            // __LATEST_COMPOSE_RELEASE_VERSION__
            const val VERSION = "1.3.0"
            const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:$VERSION"
            const val runtime = "androidx.compose.runtime:runtime:$VERSION"
            const val ui = "androidx.compose.ui:ui:$VERSION"
            const val foundationLayout =
                "androidx.compose.foundation:foundation-layout:$VERSION"
            const val material = "androidx.compose.material:material:$VERSION"
        }
    }

    object Android {
        object Tools {
            object Build {
                const val gradlePlugin = "com.android.tools.build:gradle:7.3.1"
            }
        }

        const val material = "com.google.android.material:material:1.4.0"
    }

    object Utils {
        const val UUID = "com.benasher44:uuid:0.3.1"
    }

    object AndroidX {
        object AppCompat {
            const val appCompat = "androidx.appcompat:appcompat:1.4.0"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.1"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.0-beta02"
        }
    }

    object Coroutines {
        const val Core =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.CoroutinesVersion}"
        const val Android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.CoroutinesVersion}"
    }

    object Serialization {
        const val Json =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KotlinSerializationVersion}"

    }

    object Ktor {
        object Client {
            const val Core =
                "io.ktor:ktor-client-core:${Versions.ktorVersion}"
            val contentNegotiation =
                "io.ktor:ktor-client-content-negotiation:${Versions.ktorVersion}"
            val commonLogging =
                "io.ktor:ktor-client-logging:${Versions.ktorVersion}"
            val androidOKHttp =
                "io.ktor:ktor-client-okhttp:${Versions.ktorVersion}"
            val ios = "io.ktor:ktor-client-darwin:${Versions.ktorVersion}"
            val commonSerialization =
                "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}"
            val cio = "io.ktor:ktor-client-cio:${Versions.ktorVersion}"
            val websockets = "io.ktor:ktor-client-websockets:${Versions.ktorVersion}"
        }
    }

    object Koin {
        const val Core = "io.insert-koin:koin-core:${Versions.koinVersion}"
        const val Android =
            "io.insert-koin:koin-android:${Versions.koinVersion}"
    }

    object MOKO {
        const val resources_generator =
            "dev.icerock.moko:resources-generator:${Versions.mokoVersion}"
        const val resources =
            "dev.icerock.moko:resources:${Versions.mokoVersion}"
        const val resources_compose =
            "dev.icerock.moko:resources-compose:${Versions.mokoVersion}"
    }

    object Napier {
        const val logger = "io.github.aakira:napier:${Versions.napier}"
    }

    const val precompose = "moe.tlaster:precompose:${Versions.precompose}"


    object Google {
        private const val VERSION = "20.4.0"
        const val SignIn = "com.google.android.gms:play-services-auth:$VERSION"
    }

    object KViewmodel {
        private const val VERSION = "0.14"
        val core = "com.adeo:kviewmodel:$VERSION"
        val composeExt = "com.adeo:kviewmodel-compose:$VERSION"
        val odyssey = "com.adeo:kviewmodel-odyssey:$VERSION"
    }

    val asyncImageCompose = "io.github.qdsfdhvh:image-loader:1.2.10"
}