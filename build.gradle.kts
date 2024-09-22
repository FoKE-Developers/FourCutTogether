// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.kapt) apply false
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
                    signingConfig = signingConfigs.getByName("debug")

                }
                getByName("release") {
                    // TODO: need to resolve for Hilt R8 error and change to `true`
                    isMinifyEnabled = false
                    isShrinkResources = false

                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                    // TODO: change to release keystore
                    signingConfig = signingConfigs.getByName("debug")
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
                    // TODO: change to release keystore
                    signingConfig = signingConfigs.getByName("debug")
                }
            }
        }
    }

    plugins.withId("org.jetbrains.kotlin.android") {
        apply(plugin = "kotlin-kapt")
        apply(plugin = "dagger.hilt.android.plugin")

        dependencies {
            add("implementation", libs.hilt)
            add("kapt", libs.hilt.compiler)
        }

        configure<org.jetbrains.kotlin.gradle.plugin.KaptExtension> {
            correctErrorTypes = true
        }
    }

    configurations.all {
        // resolve error of import duplicated "annotations" with "org.jetbrains.annotations
        exclude(group = "com.intellij", module = "annotations")
    }
}