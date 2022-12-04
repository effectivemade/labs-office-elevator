plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    kotlin("kapt")
}

kotlin {
    android()
    ios()


    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}