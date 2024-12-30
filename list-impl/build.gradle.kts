plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.jetbrains.compose)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    api(project(":list-api"))
    api(project(":repository-api"))

    implementation(project(":arch-util"))
    implementation(project(":arch-core"))

    // Arch
    implementation(libs.decompose.decompose)
    implementation(libs.decompose.extensionsComposeJetbrains)
    implementation(libs.essenty.lifecycle)
    implementation(libs.essenty.lifecycle.coroutines)
    implementation(libs.koin.core)
    implementation(libs.mvi)
    implementation(libs.mvi.main)
    implementation(libs.mvi.ext)

    // Multithreading
    implementation(libs.coroutines.core)

    // Compose
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    implementation(compose.uiTooling)
    implementation(compose.uiUtil)
}