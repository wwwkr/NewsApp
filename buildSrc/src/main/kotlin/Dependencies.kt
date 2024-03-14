object Versions {

    // Kotlin
    const val KOTLIN = "1.8.0"

    // AndroidX
    const val APP_COMPAT = "1.6.1"
    const val MATERIAL = "1.9.0"

    // KTX
    const val CORE = "1.10.0"
    const val KTX_LIFECYCLE = "2.6.0"

    // Compose
    const val COMPOSE_ACTIVITY = "1.7.2"
    const val COMPOSE_BOM = "2022.10.00"
    const val COMPOSE_NAVIGATION = "2.4.2"

    // TEST
    const val UNIT_TEST_JUNIT = "4.13.2"
    const val INTEGRATION_TEST_JUNIT = "1.1.5"

    // Android Test
    const val ESPRESSO_CORE = "3.5.1"

    // Hilt
    const val HILT = "2.44"

    // Network
    const val RETROFIT = "2.9.0"
    const val OKHTTP = "4.5.0"

    // Room
    const val ROOM = "2.5.2"

    // Glide
    const val COMPOSE_GLIDE = "1.3.7"
}


object Libraries {
    object Kotlin {
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-bom:${Versions.KOTLIN}"
    }

    object AndroidX {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    }

    object KTX {
        const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
        const val KTX_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.KTX_LIFECYCLE}"
        const val KTX_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.KTX_LIFECYCLE}"
    }

    object Compose {
        const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY}"
        const val COMPOSE_BOM = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
        const val COMPOSE_UI = "androidx.compose.ui:ui"
        const val COMPOSE_UI_GRAPHICS = "androidx.compose.ui:ui-graphics"
        const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
        const val COMPOSE_MATERIAL3 = "androidx.compose.material3:material3"
        const val COMPOSE_UI_TEST_JUNIT4 = "androidx.compose.ui:ui-test-junit4"
        const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling"
        const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest"
        const val COMPOSE_NAVIGATION = "androidx.navigation:navigation-compose:${Versions.COMPOSE_NAVIGATION}"
        const val COMPOSE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-compose"
    }

    object Test {
        const val JUNIT = "junit:junit:${Versions.UNIT_TEST_JUNIT}"
    }

    object AndroidTest {
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
        const val JUNIT = "androidx.test.ext:junit:${Versions.INTEGRATION_TEST_JUNIT}"
    }

    object Hilt {
        const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val KAPT_HILT = "com.google.dagger:hilt-compiler:${Versions.HILT}"
    }

    object Room {
        const val KTX_ROOM = "androidx.room:room-ktx:${Versions.ROOM}"
        const val KAPT_ROOM = "androidx.room:room-compiler:${Versions.ROOM}"
        const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
    }

    object Network {
        //Retrofit
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val RETROFIT_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"

        //Okhttp
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
        const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    }

    object Glide {
        const val COMPOSE_GLIDE = "com.github.skydoves:landscapist-glide:${Versions.COMPOSE_GLIDE}"
    }
}