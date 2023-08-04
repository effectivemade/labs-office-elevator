plugins {
    id(Plugins.Android.plugin)
    id(Plugins.MultiplatformCompose.plugin)
    id(Plugins.Kotlin.plugin)
    id(Plugins.Parcelize.plugin)
    id(Plugins.Libres.plugin)
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    dependencies{
        api(Dependencies.Ktor.Client.Core)
        implementation(Dependencies.KotlinxSerialization.json)
        implementation("io.ktor:ktor-client-cio:2.3.2")
    }
}