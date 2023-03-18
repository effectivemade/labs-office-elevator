plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(ConfigData.Android.compileSdkVersion)

    defaultConfig {
        minSdkVersion(ConfigData.Android.minSdkVersion)
        targetSdkVersion(ConfigData.Android.targetSdkVersion)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packagingOptions {
        pickFirst("lib/x86_64/libjsc.so")
        pickFirst("lib/arm64-v8a/libjsc.so")
    }

    lintOptions {
        isCheckReleaseBuilds = false
    }

    sourceSets {
        val main by getting
        main.java.setSrcDirs(listOf("src/androidMain/kotlin"))
        main.res.setSrcDirs(listOf("src/androidMain/res"))
        main.resources.setSrcDirs(
            listOf(
                "src/androidMain/resources"
            )
        )
        main.manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
}
