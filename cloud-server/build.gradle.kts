val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val google_api: String by project

plugins {
    id("kotlin-server-conventions")
    id("io.ktor.plugin") version "2.2.4"
}

application {
    mainClass.set("band.effective.office.elevator.cloud.server.ServerAppKt")
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("com.google.api-client:google-api-client:$google_api")
}

ktor {
    docker {
        externalRegistry.set(
            io.ktor.plugin.features.DockerImageRegistry.dockerHub(
                appName = provider { System.getenv()["OFFICE_ELEVATOR_EXTERNAL_DOCKER_APPNAME"] },
                username = provider { System.getenv()["OFFICE_ELEVATOR_EXTERNAL_DOCKER_USERNAME"] },
                password = provider { System.getenv()["OFFICE_ELEVATOR_EXTERNAL_DOCKER_PASSWORD"] },
            )
        )

        portMappings.set(
            listOf(
                io.ktor.plugin.features.DockerPortMapping(
                    2105,
                    80,
                    io.ktor.plugin.features.DockerPortMappingProtocol.TCP
                )
            )
        )
    }
}
