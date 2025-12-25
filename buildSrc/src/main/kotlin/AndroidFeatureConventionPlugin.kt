import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base plugins
            pluginManager.apply("flowersoflife.android-library")
            pluginManager.apply("flowersoflife.android-compose")
            pluginManager.apply("flowersoflife.android-hilt")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                // Common feature dependencies
                add("implementation", libs.findLibrary("androidx.navigation.compose").get())
                add("implementation", libs.findLibrary("hilt.navigation").get())
            }

            extensions.configure<LibraryExtension> {
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                        excludes += "/META-INF/gradle/incremental.annotation.processors"
                    }
                }
            }
        }
    }
}

