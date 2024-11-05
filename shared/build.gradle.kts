import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ktor.client.serialization)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm()
    
    listOf(
//        iosX64(),
//        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = false
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here

            // Ktor
            api(libs.ktor.client.core)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.client.logging)
            api(libs.ktor.serialization.kotlinx.json)
            api(libs.ktor.napier)

            // coil
            api(libs.coil3.core)
            api(libs.coil3.mp)
            api(libs.coil3.compose)
            api(libs.coil3.network.ktor)

            //datastore
            api(libs.datastore.preferences)

            //koin
            api(libs.koin.core)
            api(libs.koin.compose)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koinTest)
            implementation(libs.kotlinx.coroutines.test)
        }

        iosMain{
            dependencies {
                //ktor
                implementation(libs.ktor.client.ios)
                //sql
                implementation(libs.sqlite.ios.driver)
            }
        }

        androidMain.dependencies {
            //sql
            implementation(libs.sqlite.android.driver)
            // AndroidX startup
            api(libs.androidx.startup.runtime)
            //koin
            api(libs.koin.android)
        }

        jvmMain.dependencies {
            //sql
            implementation(libs.sqlite.driver)
        }
    }
}

android {
    namespace = "com.unlam.marvel.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.unlam.marvel")
        }
    }
}