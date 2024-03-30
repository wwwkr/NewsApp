plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.wwwkr.domain"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {

    // AndroidX
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.Material.MATERIAL)

    // KTX
    implementation(Libraries.KTX.CORE)

    // TEST
    testImplementation(Libraries.Test.JUNIT)
    testImplementation(Libraries.Test.KOTEST)
    testImplementation(Libraries.Test.KOTEST_ASSERTIONS)
    testImplementation(Libraries.Test.MOCKK)
    androidTestImplementation(Libraries.Test.KOCKK_ANDROID)

    // AndroidTest
    androidTestImplementation(Libraries.AndroidTest.ESPRESSO_CORE)
    androidTestImplementation(Libraries.AndroidTest.JUNIT)

    // Hilt
    implementation(Libraries.Hilt.HILT)
    kapt(Libraries.Hilt.KAPT_HILT)

}