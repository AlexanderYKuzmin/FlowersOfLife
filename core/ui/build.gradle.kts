plugins {
    id("flowersoflife.android-compose")
}

android {
    namespace = "com.kuzmin.flowersoflife.core.ui"
}

dependencies {
    api(project(":common"))
    testImplementation(project(":core:test-utils"))

    api(platform(libs.koin.bom))
    api(libs.koin.android)
    api(libs.koin.androidx.compose)
    api(libs.koin.androidx.navigation)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
