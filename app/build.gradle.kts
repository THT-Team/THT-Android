import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.tht.tht"
    compileSdk = rootProject.ext.get("compileSdk") as Int

    defaultConfig {
        applicationId = "com.tht.tht"
        minSdk = rootProject.ext.get("minSdkVersion") as Int
        targetSdk = rootProject.ext.get("targetSdk") as Int
        versionCode = rootProject.ext.get("versionCode") as Int
        versionName = rootProject.ext.get("versionName") as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val prop = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "local.properties")))
        }
        buildConfigField("String", "NAVER_CLIENT_ID", prop.getProperty("NAVER_CLIENT_ID"))
        buildConfigField("String", "NAVER_CLIENT_SECRET", prop.getProperty("NAVER_CLIENT_SECRET"))
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", prop.getProperty("KAKAO_NATIVE_APP_KEY"))
        resValue("string", "kakao_oauth_host", "kakao${prop.getProperty("KAKAO_NATIVE_APP_KEY")}")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(project(":feature:chat"))
    implementation(project(":feature:like"))
    implementation(project(":feature:setting"))
    implementation(project(":feature:signin"))
    implementation(project(":feature:tohot"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.core.splash)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    implementation(libs.hilt)
    testImplementation(libs.hilt.android.testing)
    kapt(libs.hilt.android.compiler)
    kaptTest(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)

    // Coroutines
    implementation(libs.viewmodel.ktx)
    implementation(libs.coroutines.core)
    testImplementation(libs.coroutines.test)

    // login
    implementation("com.kakao.sdk:v2-user:2.12.1")
    implementation("com.navercorp.nid:oauth-jdk8:5.4.0")

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    implementation(libs.lottie)
}
