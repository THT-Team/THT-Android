@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "tht.feature.chat"
    compileSdk = rootProject.ext.get("compileSdk") as Int
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
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


    implementation(platform(libs.composeBom))
    implementation(libs.jetpack.compose.activity)
    implementation(libs.composeUi)
    implementation(libs.composeMaterial)
    implementation(libs.composeMaterial3)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.jetpack.compose.animation)
    implementation(libs.jetpack.compose.ui.tooling)
    implementation(libs.jetpack.compose.viewmodel)
    androidTestImplementation(libs.jetpack.compose.ui.tooling.test)
    implementation(libs.jetpack.compose.navigation)

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

    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.converter)
    implementation(libs.krossbow.stomp.core)
    implementation(libs.krossbow.websocket.okhttp)
    implementation(libs.krossbow.stomp.moshi)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}
