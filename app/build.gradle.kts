plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-android")
    id("kotlin-kapt")
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.compose_version

    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        buildTypes {
            debug {
                buildConfigField("boolean", "DEBUG_MODE", "true")
                isDebuggable = true
                isJniDebuggable = true
                isMinifyEnabled = false
                isShrinkResources = false
                versionNameSuffix = "-debug"
            }

            release {
                buildConfigField("boolean", "DEBUG_MODE", "false")
                isDebuggable = false
                isJniDebuggable = false
                isMinifyEnabled = true
                isShrinkResources = true

                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    with(Dependencies.Firebase) {
        implementation(platform(bom))
        implementation(auth)
    }

    //Retrofit
    with(Dependencies.Retrofit) {
        implementation(retrofit)
        implementation(moshi)
        implementation(logging)
    }

    //Moshi
    with(Dependencies.Moshi) {
        implementation(moshi)
        kapt(codegen)
    }

    //Dagger 2
    with(Dependencies.Dagger2) {
        implementation(dagger)
        kapt(compiler)
    }

    //Logging
    with(Dependencies.Logging) {
        implementation(timber)
    }

    //Compose
    with(Dependencies.Compose) {
        implementation(ui)
        implementation(material)
        implementation(viewmodel)
        implementation(preview)
        implementation(activity)
        implementation(viewmodel)
        debugImplementation(tooling)
        androidTestImplementation(testing)
    }

    //Material UI
    with(Dependencies.Material) {
        implementation(material)
    }

    //Android X
    with(Dependencies.AndroidX) {
        implementation(appcompat)
        implementation(runtime)
    }

    //Kotlin
    with(Dependencies.Kotlin) {
        implementation(core)
    }

    //Testing
    with(Dependencies.Testing) {
        implementation(junit)
        implementation(junitExt)
        implementation(espresso)
    }

}