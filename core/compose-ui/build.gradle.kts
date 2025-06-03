import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.compose_ui"
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
}

dependencies {
    implementation(project(":core:ui"))

    implementation("com.google.accompanist:accompanist-placeholder:0.30.1")

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    implementation(libs.jetpack.compose.coil)
    implementation(libs.jetpack.compose.foundation)
    implementation(libs.app.compat.theme.adpater)

    implementation(libs.lottie.compose)

    implementation(libs.renderscript.intrinsics.replacement.toolkit)
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

}
