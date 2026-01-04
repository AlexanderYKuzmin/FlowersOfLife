package extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.kuzmin.flowersoflife.buildSrc.BuildFlavor

fun ApplicationExtension.configureFlavors() {
    flavorDimensions += "environment"
    productFlavors {
        BuildFlavor.ALL.forEach { flavor ->
            create(flavor.name) {
                dimension = "environment"
                applicationIdSuffix = flavor.applicationIdSuffix
                versionNameSuffix = flavor.versionNameSuffix

                buildConfigField("String", "BASE_URL", "\"${flavor.baseUrl}\"")
                buildConfigField("String", "ENVIRONMENT", "\"${flavor.environment}\"")

                resValue("string", "app_name", flavor.appName)
            }
        }
    }
}

fun LibraryExtension.configureFlavors() {
    flavorDimensions += "environment"
    productFlavors {
        BuildFlavor.ALL.forEach { flavor ->
            create(flavor.name) {
                dimension = "environment"

                buildConfigField("String", "BASE_URL", "\"${flavor.baseUrl}\"")
                buildConfigField("String", "ENVIRONMENT", "\"${flavor.environment}\"")
            }
        }
    }
}