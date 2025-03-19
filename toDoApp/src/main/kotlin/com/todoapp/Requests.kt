package com.todoapp

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.Credentials
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor

class Requests {
    private val client = createHttpClient()
    val objectMapper = jacksonObjectMapper()

    private fun createHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    fun get(url: String, queryParams: Map<String, Any?> = emptyMap()): ApiResponse {
        val urlBuilder = url.toHttpUrlOrNull()?.newBuilder() ?: throw IllegalStateException("Invalid URL")
        queryParams.forEach { (key, value) ->
            value?.let { urlBuilder.addQueryParameter(key, it.toString()) }
        }
        val request = Request.Builder()
            .url(urlBuilder.build().toString())
            .get()
            .build()

        val rawResponse = client.newCall(request).execute()
        return ApiResponse(rawResponse)
    }

    fun post(url: String, json: Any? = null): ApiResponse {
        val body = json?.let { objectMapper.writeValueAsString(it).toRequestBody("application/json".toMediaType()) }
            ?: "".toRequestBody(null)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        val rawResponse = client.newCall(request).execute()
        return ApiResponse(rawResponse)
    }

    fun put(url: String, json: Any? = null): ApiResponse {
        val body = json?.let { objectMapper.writeValueAsString(it).toRequestBody("application/json".toMediaType()) }
            ?: "".toRequestBody(null)

        val request = Request.Builder()
            .url(url)
            .put(body)
            .build()

        val rawResponse = client.newCall(request).execute()
        return ApiResponse(rawResponse)
    }

    fun delete(url: String, login: String, password: String): ApiResponse {
        val credentials = Credentials.basic(login, password)

        val request = Request.Builder()
            .url(url)
            .delete()
            .header("Authorization", credentials)
            .build()

        val rawResponse = client.newCall(request).execute()
        return ApiResponse(rawResponse)
    }

}
