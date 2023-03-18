object Plugins {
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Project.kotlin_version}"
    const val kotlinSerializationGradlePlugin =
        "org.jetbrains.kotlin:kotlin-serialization:${Versions.Project.kotlin_version}"
    const val AndroidBuildGradlePlugin =
        "com.android.tools.build:gradle:${Versions.Project.Android_Gradle_Plugin_version}"
    const val MokoResourceGenerator = Dependencies.MOKO.resources_generator
    const val BuildKonfigPlugin = "com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:${Versions.BuildKonfig}"
}