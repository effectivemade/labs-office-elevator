@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("kotlin-server-conventions")
    id(libs.plugins.ktor.get().pluginId)
}

application {
    mainClass.set("band.effective.office.elevator.cloud.server.ServerAppKt")
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.commonLogging)
    implementation(libs.google.appClient)
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
