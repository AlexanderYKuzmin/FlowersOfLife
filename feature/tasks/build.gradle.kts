plugins {
    id("flowersoflife.android-feature")
}

android {
    namespace = "com.kuzmin.flowersoflife.feature.tasks"
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
