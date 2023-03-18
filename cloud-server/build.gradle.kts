val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val google_api: String by project

plugins {
    id("kotlin-server-conventions")
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
