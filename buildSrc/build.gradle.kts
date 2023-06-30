plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
    jcenter()
    google()
    gradlePluginPortal()
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.plugin.android)
    implementation(libs.plugin.shadow) // NOTE(radchenko): generates jar for server builds
    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.ktor)
    implementation(libs.plugin.apacheCompress) // NOTE(radchenko): needs for `ktor` to `docker`
    implementation(libs.plugin.multiplatformCompose)
    implementation(libs.plugin.libres)
    implementation(libs.plugin.buildConfig)
    implementation(libs.plugin.serialization)
}

val rootDirProject = file("../")

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}
