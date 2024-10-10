plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "${libs.versions.id.get()}.external"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // retrofit
    implementation(libs.ok.http.base)
    implementation(libs.retrofit.base)
    implementation(libs.retrofit.gson)
    implementation(libs.google.gson)

    // qrcode-kotlin
    implementation(libs.qrcode.kotlin)

    // module dependency
    implementation(project(":domain"))

    implementation(project(":util"))
    implementation(project(":entity"))
}