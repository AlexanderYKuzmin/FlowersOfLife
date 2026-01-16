plugins {
    id("flowersoflife.android-feature")
}

android {
    namespace = "com.kuzmin.flowersoflife.feature.home"
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":data_provider:remote_data"))
    implementation(project(":data_provider:local_data"))
    implementation(project(":feature:api"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}