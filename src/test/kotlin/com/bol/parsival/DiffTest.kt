package com.bol.parsival

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty

class DiffTest {
    @Test
    fun `Can detect additions, removals and changes`() {
        val left = mapOf("a" to 1, "b" to 2)
        val right = mapOf("b" to 3, "c" to 4)

        val diff = diffOf(left, right).build()
        expectThat(diff.added()).containsExactly("c")
        expectThat(diff.removed()).containsExactly("a")
        expectThat(diff.changed()).containsExactly("b")
    }
}
