plugins {
    id("kotlin-library-conventions")
}

group = "band.effective.office.common"
version = "unspecified"

dependencies {
    implementation(Dependencies.Ktor.Server.Netty)
}