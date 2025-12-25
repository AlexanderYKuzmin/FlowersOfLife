plugins {
    id("flowersoflife.android-feature")
}

android {
    namespace = "com.kuzmin.flowersoflife.feature.auth"
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:local"))
    api(project(":common"))
    implementation(project(":feature:api"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)

    testImplementation(project(":core:test-utils"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}