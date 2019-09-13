package com.bol.parsival

import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem

fun PathItem.asOperationsMap(): Map<String, Operation> {
    return mapOf(
        "get" to get,
        "put" to put,
        "post" to post,
        "delete" to delete,
        "options" to options,
        "head" to head,
        "patch" to patch,
        "trace" to trace
    ).filterValues { it != null }
}
