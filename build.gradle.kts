buildscript {
    extra.apply {
        set("compileSdk", 32)
        set("minSdkVersion", 26)
        set("targetSdk", 32)
        set("versionCode", 1)
        set("versionName", "1.0")
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle)
        classpath(libs.androidx.navigation)
        classpath(libs.gms.google.services)
        classpath(libs.firebase.crashlytics.gradle)
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.application) apply (false)
    alias(libs.plugins.kotlin) apply (false)
    alias(libs.plugins.kotlin.jvm) apply (false)
    alias(libs.plugins.library) apply (false)
    alias(libs.plugins.ktlint) apply (false)
    alias(libs.plugins.detekt) apply (true)
}


subprojects {
    apply (plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        config.setFrom(files("$rootDir/detekt-config.yml"))
        parallel = true
    }
}
