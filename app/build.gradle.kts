plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.mapsplatform.secrets.gradle.plugin)
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
}
secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}
android {
    namespace = "com.beshoy.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.beshoy.weatherapp"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Jetpack Core
    implementation(libs.bundles.androidx)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    coreLibraryDesugaring(libs.desugar)

    // Google Play Services
    implementation(libs.playservices.location)

    // Data & Async
    implementation(libs.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization)
    implementation(libs.coil)
    // DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Test
    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.mock.android)
    testImplementation(libs.mock.agent)
    testImplementation(libs.truth)
    testImplementation(libs.coroutines.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

}