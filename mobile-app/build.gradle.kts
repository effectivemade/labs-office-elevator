plugins {
    `kotlin-dsl`
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
            // allWarningsAsErrors = true
            freeCompilerArgs = listOf(
                "-opt-in=kotlin.RequiresOptIn",
            )
        }
    }
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        jcenter()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
