@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.kuzmin.flowersoflife.core.test_utils"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.sourceCompatibility.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.targetCompatibility.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
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