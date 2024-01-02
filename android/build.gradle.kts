plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group "com.haeyum"
version "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.activity:activity-compose:1.3.0")
}

android {
    namespace = "com.haeyum.transer"

    compileSdkVersion(34)
    defaultConfig {
        applicationId = "com.haeyum.transer"
        minSdkVersion(28)
        targetSdkVersion(34)
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}