plugins {
    id("flowersoflife.android-application")
    id("com.google.gms.google-services")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":data_provider:remote_data"))
    implementation(project(":data_provider:local_data"))
    implementation(project(":core:network"))
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))
    implementation(project(":core:local"))
    implementation(project(":core:ui"))
    implementation(project(":feature:api"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:tasks"))
    implementation(project(":feature:finance"))
    implementation(project(":feature:rewards"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icon)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)

    implementation(libs.androidx.navigation.compose)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.navigation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation(libs.firebase.database)
}