package com.kuzmin.flowersoflife.buildSrc

object AppConfig {
    const val NAMESPACE = "com.kuzmin.flowersoflife"
    const val COMPILE_SDK = 36
    const val MIN_SDK = 28
    const val TARGET_SDK = 36
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
}

sealed class BuildFlavor(
    val name: String,
    val applicationIdSuffix: String?,
    val versionNameSuffix: String?,
    val baseUrl: String,
    val environment: String,
    val appName: String,
) {
    object Dev : BuildFlavor(
        name = "dev",
        applicationIdSuffix = ".dev",
        versionNameSuffix = "-dev",
        baseUrl = "http://localhost:8080/api/",
        environment = "dev",
        appName = "FlowersOfLife Dev",
    )

    object Prod : BuildFlavor(
        name = "prod",
        applicationIdSuffix = null,
        versionNameSuffix = null,
        baseUrl = "https://api.yourdomain.com/api/v1/",
        environment = "prod",
        appName = "FlowersOfLife",
    )

    companion object {
        val ALL = listOf(Dev, Prod)
    }
}