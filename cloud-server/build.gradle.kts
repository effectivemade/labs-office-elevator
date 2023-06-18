plugins {
    id("kotlin-server-conventions")
    id(Plugins.Ktor.plugin)
}

application {
    mainClass.set("band.effective.office.elevator.cloud.server.ServerAppKt")
}

dependencies {
    implementation(Dependencies.Ktor.Client.Core)
    implementation(Dependencies.Ktor.Client.CIO)
    implementation(Dependencies.Ktor.Client.CommonLogging)
    implementation(Dependencies.Google.ApiClient)
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
