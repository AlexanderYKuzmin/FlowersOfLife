plugins {
    id("flowersoflife.android-compose")
    id("flowersoflife.android-hilt")
}

android {
    namespace = "com.kuzmin.flowersoflife.core.ui"
}

dependencies {
    api(project(":common"))

    testImplementation(project(":core:test-utils"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
