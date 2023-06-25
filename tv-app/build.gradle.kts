import java.util.Properties
import java.io.FileInputStream

plugins {
    id(Plugins.Android.plugin)
    kotlin("android")
    kotlin("kapt")
    id(Plugins.Hilt.plugin) version "2.44" apply true
}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

android {
    namespace  = "band.effective.office.tv"
    compileSdk = 33

    defaultConfig {
        applicationId  = "band.effective.office.tv"
        minSdk  = 24
        targetSdk = 33

        versionCode =  1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField ("String", "apiLeaderUrl", localProperties["apiLeaderUrl"].toString())
        buildConfigField ("String", "apiMattermostUrl", localProperties["apiMattermostUrl"].toString())
        buildConfigField ("String", "mattermostBotToken", localProperties["mattermostBotToken"].toString())
        buildConfigField ("String", "apiSynologyUrl", localProperties["synologyIP"].toString())
        buildConfigField ("String", "synologyLogin", localProperties["synologyLogin"].toString())
        buildConfigField ("String", "synologyPassword", localProperties["synologyPassword"].toString())
        buildConfigField ("String", "folderPathPhotoSynology", localProperties["folderPathPhotoSynology"].toString())
        buildConfigField ("String", "uselessFactsApi", localProperties["uselessFactsApi"].toString())
        buildConfigField ("String", "mattermostBotDirectId", localProperties["mattermostBotDirectId"].toString())
        buildConfigField ("String", "duolingoUrl", localProperties["duolingoUrl"].toString())
        buildConfigField("String", "notionToken", localProperties["NOTION_TOKEN"].toString())
        buildConfigField("String", "notionDatabaseId", localProperties["NOTION_DATABASE_ID"].toString())
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        compose =  true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.activityCompose)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.KotlinxCoroutines.android)
    implementation(Dependencies.AndroidX.activityKtx)

    //for tv
    implementation(Dependencies.TvDeps.tvFoundation)
    implementation(Dependencies.TvDeps.tvMaterial)

    //Retrofit
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Okhttp.okhttp)
    implementation(Dependencies.Okhttp.okhttpLoggingIntersepter)
    implementation(Dependencies.Retrofit.moshiConverter)

    // moshi
    implementation(Dependencies.Moshi.moshi)
    implementation(Dependencies.Moshi.moshiAdapter)
    kapt(Dependencies.Moshi.moshiCodegen)

    //hilt di
    implementation(Dependencies.Hilt.hilt)
    implementation(Dependencies.Hilt.hiltNav)
    kapt(Dependencies.Hilt.hiltCompiller)

    //qr
    implementation(Dependencies.QRCodeGenerator.zxing)

    //navigation
    implementation(Dependencies.ComposeNavigation.navigation)

    //coil
    implementation(Dependencies.Coil.coil)

    //notion
    implementation(Dependencies.NotionSDK.notion)
}
kapt {
    correctErrorTypes = true
}
