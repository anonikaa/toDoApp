package com.todoapp
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.Response
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.reflect.KClass


class ApiResponse (rawResponse: Response) {

    val statusCode: Int = rawResponse.code
    val body: String = rawResponse.body?.string() ?: ""
    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    inline fun <reified T> assertEntity(expected: T) {
        val actual: T = ObjectMapper().readValue(this.body, object : TypeReference<T>() {})
        assertStatusCode(201, 200, 204)
        assertEquals(expected, actual)
    }

    fun <T : Any> entity(clazz: KClass<T>): T {
        assertStatusCode(201, 200, 204)
        return objectMapper.readValue(body, clazz.java)
    }

    fun assertStatusCode(vararg expectedCodes: Int): ApiResponse {
        assertThat(statusCode).isIn(expectedCodes.toList())
        return this
    }
}
