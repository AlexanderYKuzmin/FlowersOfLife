plugins {
    id("flowersoflife.android-library")
    id("flowersoflife.android-hilt")
}

android {
    namespace = "com.kuzmin.flowersoflife.data_provider.local"
}

dependencies {
    api(project(":common"))
    implementation(project(":core:local"))
    implementation(project(":core:domain"))
    implementation(project(":feature:auth"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.datastore)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
