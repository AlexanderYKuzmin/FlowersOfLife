plugins {
    id("flowersoflife.android-library")
}

android {
    namespace = "com.kuzmin.flowersoflife.core"
}

dependencies {
    implementation(project(":common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)

    //Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.navigation)

    // Retrofit
    api(libs.retrofit)
    api(libs.retrofit.gson)
    api(libs.okhttp)
    api(libs.okhttp.logging)
    api(libs.gson)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
