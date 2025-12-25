plugins {
    id("flowersoflife.android-library")
    id("flowersoflife.android-hilt")
}

android {
    namespace = "com.kuzmin.flowersoflife.core.local"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.datastore)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
