package com.kuzmin.flowersoflife.core.interceptors

import com.kuzmin.flowersoflife.core.api.AuthService
import com.kuzmin.flowersoflife.core.constants.Constants.AUTHORIZATION_HEADER
import com.kuzmin.flowersoflife.core.constants.Constants.BEARER
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authService: AuthService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = authService.getCurrentUser()?.getIdToken(false)?.result?.token
        val request = chain.request().newBuilder()
            .apply {
                token?.let {
                    addHeader(AUTHORIZATION_HEADER, BEARER + it)
                }
            }
            .build()
        return chain.proceed(request)
    }
}