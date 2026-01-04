plugins {
    id("flowersoflife.android-library")
}

android {
    namespace = "com.kuzmin.flowersoflife.core.domain"
}

dependencies {
    api(project(":common"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
