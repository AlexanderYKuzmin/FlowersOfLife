import com.android.build.api.dsl.ApplicationExtension
import com.kuzmin.flowersoflife.buildSrc.AppConfig
import extensions.configureFlavors
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply(libs.findPlugin("androidApplication").get().get().pluginId)
                apply(libs.findPlugin("kotlinAndroid").get().get().pluginId)
                apply(libs.findPlugin("composeCompiler").get().get().pluginId)
            }

            extensions.configure<ApplicationExtension> {
                namespace = AppConfig.NAMESPACE
                compileSdk = AppConfig.COMPILE_SDK

                defaultConfig {
                    applicationId = AppConfig.NAMESPACE
                    minSdk = AppConfig.MIN_SDK
                    targetSdk = AppConfig.TARGET_SDK
                    versionCode = AppConfig.VERSION_CODE
                    versionName = AppConfig.VERSION_NAME

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                configureFlavors()

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                buildFeatures {
                    compose = true
                    buildConfig = true
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                        excludes += "/META-INF/gradle/incremental.annotation.processors"
                    }
                }
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
                }
            }
        }
    }
}