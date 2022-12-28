buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.android.gradle)
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
