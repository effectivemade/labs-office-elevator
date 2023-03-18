plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
}

version = "0.0.1"

kotlin {
    android()
    ios()

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}