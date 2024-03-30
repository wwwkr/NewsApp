plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.wwwkr.newsapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.wwwkr.newsapp"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.5"
    }
    packaging  {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Module
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation (Libraries.KTX.CORE)
    implementation(platform(Libraries.Kotlin.KOTLIN))
    implementation (Libraries.KTX.KTX_RUNTIME)

    // Compose
    implementation (Libraries.Compose.COMPOSE_ACTIVITY)
    implementation(platform(Libraries.Compose.COMPOSE_BOM))
    implementation (Libraries.Compose.COMPOSE_UI)
    implementation (Libraries.Compose.COMPOSE_UI_GRAPHICS)
    implementation (Libraries.Compose.COMPOSE_UI_TOOLING_PREVIEW)
    implementation (Libraries.Compose.COMPOSE_MATERIAL3)
    implementation (Libraries.Compose.COMPOSE_NAVIGATION)
    implementation (Libraries.Compose.COMPOSE_RUNTIME)

    androidTestImplementation(platform(Libraries.Compose.COMPOSE_BOM))
    debugImplementation (Libraries.Compose.COMPOSE_UI_TOOLING)
    debugImplementation (Libraries.Compose.COMPOSE_UI_TEST_MANIFEST)
    androidTestImplementation (Libraries.Compose.COMPOSE_UI_TEST_JUNIT4)

    // Test
    testImplementation (Libraries.Test.JUNIT)
    androidTestImplementation (Libraries.AndroidTest.JUNIT)
    androidTestImplementation (Libraries.AndroidTest.ESPRESSO_CORE)

    // Hilt
    implementation(Libraries.Hilt.HILT)
    kapt(Libraries.Hilt.KAPT_HILT)

    // Room
    implementation(Libraries.Room.ROOM)
    implementation(Libraries.Room.KTX_ROOM)
    kapt(Libraries.Room.KAPT_ROOM)

    // Retrofit
    implementation(Libraries.Network.RETROFIT)
    implementation(Libraries.Network.RETROFIT_CONVERTER)

    // Okhttp
    implementation(Libraries.Network.OKHTTP)
    implementation(Libraries.Network.OKHTTP_LOGGING_INTERCEPTOR)

    // Glide
    implementation(Libraries.Glide.COMPOSE_GLIDE)
}

