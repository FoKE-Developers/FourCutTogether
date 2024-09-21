// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}

subprojects {
    // TODO: convert to config.gradle.kts
    // apply(from = "config.gradle.kts")

    // common Android settings
    plugins.withType<com.android.build.gradle.BasePlugin> {
        configure<com.android.build.gradle.BaseExtension> {
            compileSdkVersion = libs.versions.sdk.compile.get()
            defaultConfig {
                minSdk = libs.versions.sdk.min.get().toInt()
                targetSdk = libs.versions.sdk.target.get().toInt()
                versionCode = libs.versions.app.version.code.get().toInt()
                versionName = libs.versions.app.version.name.get()

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    // settings for application
    plugins.withType<com.android.build.gradle.AppPlugin> {
        configure<com.android.build.gradle.AppExtension> {
            defaultConfig {
                // consumerProguardFiles = "consumer-rules.pro" TODO: resolve error
            }

            buildTypes {
                getByName("debug") {
                    isMinifyEnabled = false
                    isShrinkResources = false
                    // TODO: signing
                }
                getByName("release") {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
        }
    }

    // settings for library
    plugins.withType<com.android.build.gradle.LibraryPlugin> {
        configure<com.android.build.gradle.LibraryExtension> {
            defaultConfig {
                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
        }
    }
}