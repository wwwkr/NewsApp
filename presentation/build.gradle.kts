plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
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
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packaging  {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation (Libraries.KTX.CORE)
    implementation(platform(Libraries.Kotlin.KOTLIN))
    implementation (Libraries.KTX.KTX_RUNTIME)
    implementation (Libraries.Compose.COMPOSE_ACTIVITY)
    implementation(platform(Libraries.Compose.COMPOSE_BOM))
    implementation (Libraries.Compose.COMPOSE_UI)
    implementation (Libraries.Compose.COMPOSE_UI_GRAPHICS)
    implementation (Libraries.Compose.COMPOSE_UI_TOOLING_PREVIEW)
    implementation (Libraries.Compose.COMPOSE_UI_MATERIAL3)
    testImplementation (Libraries.Test.JUNIT)
    androidTestImplementation (Libraries.AndroidTest.JUNIT)
    androidTestImplementation (Libraries.AndroidTest.ESPRESSO_CORE)
    androidTestImplementation(platform(Libraries.Compose.COMPOSE_BOM))
    androidTestImplementation (Libraries.Compose.COMPOSE_UI_TEST_JUNIT4)
    debugImplementation (Libraries.Compose.COMPOSE_UI_TOOLING)
    debugImplementation (Libraries.Compose.COMPOSE_UI_TEST_MANIFEST)
}