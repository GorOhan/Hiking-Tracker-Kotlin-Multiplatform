plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization").version("1.9.23")
    id("com.squareup.sqldelight").version("1.5.5")

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation (libs.ktor.client.logging)
            implementation(libs.kotlinx.datetime)
            implementation(libs.logback.classic)
            implementation("com.squareup.sqldelight:runtime:1.5.5")


        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation("com.squareup.sqldelight:android-driver:1.5.5")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.ohanyan.xhike"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.ohanyan.xhike.shared.cache"
    }
}
