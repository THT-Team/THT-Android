buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.3.1")
    }
}

plugins {
    id ("com.android.application") version ("7.3.1") apply (false)
    id ("com.android.library") version ("7.3.1") apply (false)
    id ("org.jetbrains.kotlin.android") version ("1.7.20") apply (false)
    id ("org.jetbrains.kotlin.jvm") version ("1.7.10") apply (false)
    id ("org.jlleitschuh.gradle.ktlint") version ("10.3.0") apply (false)
    id ("io.gitlab.arturbosch.detekt") version ("1.22.0")
}


subprojects {
    apply (plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        config.setFrom(files("$rootDir/detekt-config.yml"))
        parallel = true
    }
}