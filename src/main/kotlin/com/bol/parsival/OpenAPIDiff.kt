package com.bol.parsival

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.Paths

class OpenAPIDiff(
    private val left: OpenAPI,
    private val right: OpenAPI
) {
    fun get(): List<Difference> {
        return walkPaths(left.paths, right.paths)
    }

    private fun walkPaths(left: Paths, right: Paths): List<Difference> {
        return diffOf(left, right)
            .mapAdded { PathAdded(it) }
            .mapRemoved { PathRemoved(it) }
            .mapChanged(::walkPathItems)
            .build()
            .differences()
    }

    private fun walkPathItems(key: String, oldPath: PathItem, newPath: PathItem): Difference {
        val oldOperations = oldPath.asOperationsMap()
        val newOperations = newPath.asOperationsMap()

        val differences = diffOf(oldOperations, newOperations)
            .mapAdded(::OperationAdded)
            .mapRemoved(::OperationRemoved)
            .mapChanged(::walkOperations)
            .build()
            .differences()

        return PathModified(key, differences)
    }

    private fun walkOperations(key: String, oldOp: Operation, newOp: Operation): Difference {
        val differences = diffOf(oldOp.responses, newOp.responses)
            .mapAdded { ResponseAdded(it.toInt()) }
            .mapRemoved  { ResponseRemoved(it.toInt()) }
            .build()
            .differences()

        return OperationModified(key, differences)
    }
}
