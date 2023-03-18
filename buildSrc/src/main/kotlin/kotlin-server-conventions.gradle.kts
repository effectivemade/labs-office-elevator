val logback_version: String by project
val ktor_version: String by project

plugins {
    id("kotlin-common-conventions")
    id("com.github.johnrengelman.shadow")
    application
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation(project(":common"))
}