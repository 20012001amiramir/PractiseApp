package com.example.practiseapp.data.network.interceptors

import com.example.practiseapp.domain.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor
@Inject constructor(private val sessionManager: SessionManager): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Token $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}
