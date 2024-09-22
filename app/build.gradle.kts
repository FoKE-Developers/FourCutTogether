plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = libs.versions.id.get()

    defaultConfig {
        applicationId = libs.versions.id.get()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // module dependency
    implementation(project(":presenter"))
    implementation(project(":data"))
    implementation(project(":external"))

    implementation(project(":domain"))

    implementation(project(":util"))
    implementation(project(":entity"))
}