
plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    gradlePluginPortal()
}

dependencies {
    implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    implementation(Dependencies.JetBrains.Kotlin.gradlePlugin)
    implementation(Dependencies.Android.Tools.Build.gradlePlugin)
    implementation(Dependencies.JetBrains.Kotlin.serializationPlugin)
    implementation("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.13.3")
    implementation("org.apache.commons:commons-compress:1.21")
}

val rootDirProject = file("../")

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}
