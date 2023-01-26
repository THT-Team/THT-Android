plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.coroutines.core)
    testImplementation(libs.coroutines.test)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.assertj.core)
}
