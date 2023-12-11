plugins {
    kotlin("jvm")
    id(Plugins.Serialization.plugin)
    id("io.ktor.plugin")
    application
}

group = "band.effective.office.tvServer"
version = "0.0.1"

application{
    mainClass.set("band.effective.office.tvServer.ApplicationKt")
}

ktor{
    docker{
        localImageName.set("tv-server-image")
    }
}

dependencies{
    implementation(Plugins.Serialization.implementation)
    implementation(Dependencies.KotlinxSerialization.json)
    implementation(Dependencies.Ktor.Client.Core)
    implementation(Dependencies.Ktor.Client.CIO)
    implementation(Dependencies.Ktor.Client.jsonSerialization)
    implementation(Dependencies.Ktor.Client.negotiation)
    implementation(Dependencies.Ktor.Client.Auth)
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Ktor.Client.logging)

    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.1")
    implementation("io.insert-koin:koin-ktor:3.5.1")

}