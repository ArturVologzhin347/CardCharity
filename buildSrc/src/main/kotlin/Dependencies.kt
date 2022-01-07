object Dependencies {

    object Coil {
        const val coil = "io.coil-kt:coil-compose:1.4.0"
    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:29.0.3"
        const val auth = "com.google.firebase:firebase-auth-ktx"
        const val authServices = "com.google.android.gms:play-services-auth:20.0.1"
    }

    object Kotlin {
        const val kotlin_version = "1.7.0"
        const val core = "androidx.core:core-ktx:$kotlin_version"
    }

    /*
    implementation("androidx.compose.material:material-icons-core:1.0.5")
    implementation("androidx.compose.material:material-icons-extended:1.0.5")
      implementation("androidx.compose.foundation:foundation:1.0.5")
     */
    object Compose {
        const val compose_version = "1.0.5"

        const val material = "androidx.compose.material:material:$compose_version"
        const val ui = "androidx.compose.ui:ui:$compose_version"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0"
        const val preview = "androidx.compose.ui:ui-tooling-preview:$compose_version"
        const val testing = "androidx.compose.ui:ui-test-junit4:$compose_version"
        const val tooling = "androidx.compose.ui:ui-tooling:$compose_version"
        const val activity = "androidx.activity:activity-compose:1.3.0-alpha06"
    }

    object Accompanist {
        const val insets = "com.google.accompanist:accompanist-insets-ui:0.21.5-rc"
        const val systemuicontroller = "com.google.accompanist:accompanist-systemuicontroller:0.18.0"
    }

    object Material {
        const val material_version = "1.4.0"
        const val material = "com.google.android.material:material:$material_version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.4.0"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
    }

    object Dagger2 {
        const val dagger_version = "2.40.5"
        const val dagger = "com.google.dagger:dagger:$dagger_version"
        const val compiler = "com.google.dagger:dagger-compiler:$dagger_version"
    }

    object Logging {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }

    object Retrofit {
        const val retrofit_version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
        const val moshi = "com.squareup.retrofit2:converter-moshi:2.4.0"
        const val logging = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3"
    }

    object Moshi {
        const val moshi_version = "1.13.0"
        const val moshi = "com.squareup.moshi:moshi-kotlin:$moshi_version"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$moshi_version"
    }

    object Testing {
        const val junit = "junit:junit:4.12"
        const val junitExt = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Plugins {
        const val googleServices = "com.google.gms:google-services:4.3.10"
        const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.4"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31"
    }
}