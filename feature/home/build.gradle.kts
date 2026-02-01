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
    implementation(project(":core:local"))
    implementation(project(":data_provider:remote_data"))
    implementation(project(":data_provider:local_data"))
    implementation(project(":feature:api"))

    implementation(libs.coil.compose)

    // Vico Charts
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}