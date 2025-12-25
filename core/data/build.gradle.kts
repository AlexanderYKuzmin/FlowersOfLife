plugins {
    id("flowersoflife.android-library")
}

android {
    namespace = "com.kuzmin.flowersoflife.core.data"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
