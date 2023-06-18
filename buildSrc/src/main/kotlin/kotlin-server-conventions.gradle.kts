plugins {
    id("kotlin-common-conventions")
    id("com.github.johnrengelman.shadow")
    application
}

dependencies {
    implementation(Dependencies.Ktor.Server.Logback)
    implementation(Dependencies.Ktor.Server.Netty)
    implementation(project(":common"))
}