plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    //implementation(Dependencies.JetBrains.Compose.gradlePlugin)
    implementation(Dependencies.JetBrains.Kotlin.gradlePlugin)
    implementation(Dependencies.Android.Tools.Build.gradlePlugin)
    implementation(Dependencies.JetBrains.Kotlin.serializationPlugin)
    implementation("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.13.3")
}

val rootDirProject = file("../")

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}
