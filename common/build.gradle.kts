val ktor_version: String by project
plugins {
    id("kotlin-library-conventions")
}

group = "band.effective.office.common"
version = "unspecified"

dependencies {
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
}