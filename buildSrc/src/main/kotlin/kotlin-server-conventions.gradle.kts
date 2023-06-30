import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("kotlin-common-conventions")
    id("com.github.johnrengelman.shadow")
    application
}
val libs = the<LibrariesForLibs>()

dependencies {
    implementation(libs.ktor.server.logback)
    implementation(libs.ktor.server.netty)
    implementation(project(":common"))
}