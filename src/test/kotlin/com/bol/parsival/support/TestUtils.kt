package com.bol.parsival.support

import com.bol.parsival.OpenAPIDiffTest
import io.swagger.parser.OpenAPIParser
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.parser.core.models.ParseOptions
import org.junit.jupiter.api.fail

fun parseOpenAPI(filename: String): OpenAPI {
    val swaggerAsString = OpenAPIDiffTest::class.java.getResource(filename).readText()

    val parser = OpenAPIParser()
    val options = ParseOptions()
    val result = parser.readContents(swaggerAsString, emptyList(), options)

    if (result.messages.isNotEmpty()) {
        fail(result.messages.joinToString())
    }

    return result.openAPI
}
