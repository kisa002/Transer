import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.haeyum"
version = "1.0-SNAPSHOT"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "19"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "Transer"
            packageVersion = "1.0.0"
            copyright = "HAEYUM"
            description = "Transer"
            modules("java.sql")

            macOS {
                dockName = "Transer"
                appCategory = "public.app-category.utilities"
                iconFile.set(project.file("src/jvmMain/resources/drawable/icon.icns"))
            }

            windows {
                iconFile.set(project.file("src/jvmMain/resources/drawable/icon.ico"))
            }

            linux {
                iconFile.set(project.file("src/jvmMain/resources/drawable/icon.icns"))
            }
        }
    }
}
