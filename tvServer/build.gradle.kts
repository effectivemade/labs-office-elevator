plugins {
    kotlin("jvm")
    id(Plugins.Serialization.plugin)
    id("io.ktor.plugin")
}

dependencies{
    implementation(Plugins.Serialization.implementation)
    implementation(Dependencies.KotlinxSerialization.json)
    implementation(Dependencies.Ktor.Client.Core)
    implementation(Dependencies.Ktor.Client.CIO)
    implementation(Dependencies.Ktor.Client.jsonSerialization)
    implementation(Dependencies.Ktor.Client.negotiation)
    implementation(Dependencies.Koin.core)

    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.1")
    implementation("io.insert-koin:koin-ktor:3.5.1")
}