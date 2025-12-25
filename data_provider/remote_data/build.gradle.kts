plugins {
    id("flowersoflife.android-library")
    id("flowersoflife.android-hilt")
}

android {
    namespace = "com.kuzmin.flowersoflife.data_provider"
}

dependencies {
    api(project(":common"))
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(project(":feature:auth"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
