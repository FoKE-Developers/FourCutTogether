// TODO: use settings if needed
//       check libs.versions.toml files for details of versions

object AppConfig {
//    val id = "com.foke.together"
//    val versionCode = 1
//    val versionName = "1.0.0"
//
//    val compileSdk = libs.versions.sdk.compile.get().toInt()
//    val minSdk = libs.versions.sdk.min.get().toInt()
//    val targetSdk = libs.versions.sdk.target.get().toInt()

    val javaVersion = JavaVersion.VERSION_17
    val jvmTarget = "17"
    val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}