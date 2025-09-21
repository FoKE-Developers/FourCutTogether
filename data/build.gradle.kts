import org.gradle.internal.extensions.stdlib.capitalized
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "${libs.versions.id.get()}.data"
}

protobuf {
    protoc {
        artifact = libs.versions.protoc.get()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

androidComponents {
    onVariants(selector().all()) { variant ->
        afterEvaluate {
            val capName = variant.name.capitalized()
            tasks.getByName<KotlinCompile>("ksp${capName}Kotlin") {
                setSource(tasks.getByName("generate${capName}Proto").outputs)
            }
        }
    }
}

dependencies {
    // work manager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.hilt.compiler)

    // room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // retrofit
    implementation(libs.ok.http.base)
    implementation(libs.retrofit.base)
    implementation(libs.retrofit.gson)
    implementation(libs.google.gson)

    // serialization
    implementation(libs.serialization)
    implementation(libs.serialization.converter)

    // proto datastore
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)
    implementation(libs.datastore.preferences.core)
    implementation(libs.protobuf.javalite)

    // camerax
    implementation(libs.camerax.core)
    // test
    kspAndroidTest(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    // module dependency
    implementation(project(":domain"))

    implementation(project(":util"))
    implementation(project(":entity"))
}