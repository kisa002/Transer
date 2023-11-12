plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
}

group = "com.haeyum"
version = "1.0-SNAPSHOT"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "19"
            }
        }
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "19"
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "shared"
        }
    }
    
//    sourceSets {
//        val commonMain by getting {
//            dependencies {
//                //put your multiplatform dependencies here
//            }
//        }
//        val commonTest by getting {
//            dependencies {
//                implementation(kotlin("test"))
//            }
//        }
//    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)

                // Ktor
                api("io.ktor:ktor-client-core:${extra["ktor.version"]}")
                api("io.ktor:ktor-client-cio:${extra["ktor.version"]}")
                api("io.ktor:ktor-client-logging:${extra["ktor.version"]}")
                api("io.ktor:ktor-client-content-negotiation:${extra["ktor.version"]}")
                api("io.ktor:ktor-serialization-kotlinx-json:${extra["ktor.version"]}")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

                // Koin Core features
                api("io.insert-koin:koin-core:${extra["koin.version"]}")
                // Koin for Ktor
//                api("io.insert-koin:koin-ktor:${extra["koin.ktor"]}")

                // SQLDelight
                api("com.squareup.sqldelight:coroutines-extensions:1.5.4")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.2.0")
                api("androidx.core:core-ktx:1.3.1")
                api("androidx.compose.material:material-icons-extended:1.3.0")

                // Koin main features for Android
                api("io.insert-koin:koin-android:${extra["koin.android.version"]}")
//                // Java Compatibility
                api("io.insert-koin:koin-android-compat:${extra["koin.android.version"]}")
//                // Jetpack WorkManager
                api("io.insert-koin:koin-androidx-workmanager:${extra["koin.android.version"]}")
//                // Navigation Graph
                api("io.insert-koin:koin-androidx-navigation:${extra["koin.android.version"]}")
//                // Jetpack Compose
                api("io.insert-koin:koin-androidx-compose:${extra["koin.android.compose.version"]}")

                api("com.squareup.sqldelight:android-driver:${extra["sqldelight.version"]}")
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
                api("com.squareup.sqldelight:sqlite-driver:${extra["sqldelight.version"]}")
                api("com.github.kwhat:jnativehook:2.2.2")
            }
        }
        val desktopTest by getting
    }
}

android {
    namespace = "com.haeyum.android"

    compileSdkVersion(34)
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs(
            "src/commonMain/resources",
            "src/androidMain/resources"
        )
    }
    defaultConfig {
        minSdkVersion(28)
        targetSdkVersion(34)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
}
//dependencies {
//    implementation("androidx.compose.ui:ui-graphics-android:1.5.4")
//    implementation("androidx.compose.foundation:foundation-desktop:1.6.0-alpha08")
//}

sqldelight {
    database("TranserDatabase") {
        packageName = "com.haeyum.shared"
    }
}