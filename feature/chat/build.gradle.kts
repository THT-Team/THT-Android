@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("org.jlleitschuh.gradle.ktlint")
    id("dagger.hilt.android.plugin")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:compose-ui"))
    implementation(project(":domain"))
    implementation(project(":data"))

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

    implementation(libs.okhttp)
    implementation("io.reactivex.rxjava2:rxjava:2.2.10")

    implementation("io.github.jaeyunn15:thunder:1.1.0") // must required
    implementation("io.github.jaeyunn15:thunder-okhttp:1.1.0") // must required
    implementation("io.github.jaeyunn15:thunder-stomp:1.1.0")
}
