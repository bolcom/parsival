package com.bol.parsival

import com.bol.parsival.support.parseOpenAPI
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

class OpenAPIDiffTest {
    @Test
    fun `Can detect path removal`() {
        val left = parseOpenAPI("/swagger-v2/remove-path-left.yml")
        val right = parseOpenAPI("/swagger-v2/remove-path-right.yml")
        val diff = OpenAPIDiff(left, right).get()
        expectThat(diff).containsExactly(
            PathRemoved("/pet")
        )
    }

    @Test
    fun `Can detect path addition`() {
        val left = parseOpenAPI("/swagger-v2/remove-path-right.yml")
        val right = parseOpenAPI("/swagger-v2/remove-path-left.yml")
        val diff = OpenAPIDiff(left, right).get()
        expectThat(diff).containsExactly(
            PathAdded("/pet")
        )
    }

    @Test
    fun `Can detect operation removal`() {
        val left = parseOpenAPI("/swagger-v2/remove-operation-left.yml")
        val right = parseOpenAPI("/swagger-v2/remove-operation-right.yml")
        val diff = OpenAPIDiff(left, right).get()
        expectThat(diff).containsExactly(
            PathModified("/pet", listOf(
                OperationRemoved("post")
            ))
        )
    }

    @Test
    fun `Can detect operation addition`() {
        val left = parseOpenAPI("/swagger-v2/remove-operation-right.yml")
        val right = parseOpenAPI("/swagger-v2/remove-operation-left.yml")
        val diff = OpenAPIDiff(left, right).get()
        expectThat(diff).containsExactly(
            PathModified(
                "/pet", listOf(
                    OperationAdded("post")
                )
            )
        )
    }
    @Test
    fun `Can remove a response`() {
        val left = parseOpenAPI("/swagger-v2/remove-response-left.yml")
        val right = parseOpenAPI("/swagger-v2/remove-response-right.yml")
        val diff = OpenAPIDiff(left, right).get()
        expectThat(diff).containsExactly(
            PathModified(
                "/pet", listOf(
                    OperationModified("post", listOf(
                        ResponseRemoved(405)
                    ))
                )
            )
        )
    }
}
