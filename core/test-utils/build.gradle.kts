plugins {
    id("flowersoflife.android-library")
}

android {
    namespace = "com.kuzmin.flowersoflife.core.test_utils"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    api(libs.kotlinx.coroutines.test)
    api(libs.junit)
    api(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
