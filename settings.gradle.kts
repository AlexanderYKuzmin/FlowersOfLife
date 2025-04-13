pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Flowers Of Life"
include(":app")
include(":common")
include(":data_provider:remote_data")
include(":data_provider:local_data")
include(":core:network")
include(":feature:auth")
include(":core:navigation")
include(":feature:home")
include(":feature:tasks")
include(":feature:rewards")
include(":feature:finance")
include(":core:domain")
include(":core:local")
include(":core:ui")
include(":feature:api")
