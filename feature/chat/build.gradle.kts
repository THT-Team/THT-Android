@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    kotlin("plugin.serialization") version "1.8.0"
}

android {
    namespace = "tht.feature.chat"
    compileSdk = rootProject.ext.get("compileSdk") as Int

    defaultConfig {
        minSdk = rootProject.ext.get("minSdkVersion") as Int
        targetSdk = rootProject.ext.get("targetSdk") as Int

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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:compose-ui"))
    implementation(project(":domain"))
    implementation(project(":feature:setting"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
    implementation(libs.kotlin.collections.immutable)

    implementation(libs.jetpack.compose.activity)
    implementation(libs.jetpack.compose.material)
    implementation(libs.jetpack.compose.animation)
    implementation(libs.jetpack.compose.ui.tooling)
    implementation(libs.jetpack.compose.viewmodel)
    androidTestImplementation(libs.jetpack.compose.ui.tooling.test)

    implementation(libs.hilt)
    testImplementation(libs.hilt.android.testing)
    kapt(libs.hilt.android.compiler)
    kaptTest(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.jetpack.compose.hilt.navigation)

//    implementation("com.beust:klaxon:5.6")
//    implementation("com.squareup.okhttp:okhttp-ws:2.7.5")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
//    testImplementation("com.squareup.okhttp:mockwebserver:2.7.5")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.22")
//    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
//
    implementation(libs.krossbow.stomp.core)
    implementation(libs.krossbow.websocket.okhttp)
    implementation(libs.krossbow.stomp.moshi)
    implementation(libs.okhttp.logging.interceptor)
    implementation("org.hildan.krossbow:krossbow-stomp-core:7.0.0")
    implementation("org.hildan.krossbow:krossbow-stomp-kxserialization:7.0.0")
    implementation("org.hildan.krossbow:krossbow-stomp-kxserialization-json:7.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

}
