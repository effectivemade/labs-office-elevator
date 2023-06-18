object Dependencies {

    object Ktor {
        const val ktorVersion = "2.3.1"
        object Client {
            const val Core =
                "io.ktor:ktor-client-core:$ktorVersion"
            const val CommonLogging =
                "io.ktor:ktor-client-logging:$ktorVersion"
            const val CIO = "io.ktor:ktor-client-cio:$ktorVersion"
        }

        object Server {
            const val Netty = "io.ktor:ktor-server-netty-jvm:$ktorVersion"
            const val Logback = "ch.qos.logback:logback-classic:1.4.4"
        }
    }


    object Google {
        private const val VERSION = "20.4.0"
        const val SignIn = "com.google.android.gms:play-services-auth:$VERSION"
        const val ApiClient = "com.google.api-client:google-api-client:2.1.1"
    }
}