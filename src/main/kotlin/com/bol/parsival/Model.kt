package com.bol.parsival

sealed class Difference
sealed class Added : Difference()
sealed class Removed : Difference()
sealed class Modified: Difference()

data class PathAdded(val path: String) : Added()
data class PathRemoved(val path: String) : Removed()
data class PathModified(
    val path: String,
    val operations: List<Difference>
) : Modified()

sealed class OperationDifference: Difference() {
    abstract val op: String
}
data class OperationAdded(override val op: String) : OperationDifference()
data class OperationRemoved(override val op: String) : OperationDifference()
data class OperationModified(
    override val op: String,
    val responses: List<Difference>
): OperationDifference()

sealed class ResponseDifference : Difference() {
    abstract val status: Int
}

data class ResponseAdded(override val status: Int) : ResponseDifference()
data class ResponseRemoved(override val status: Int) : ResponseDifference()
data class ResponseModified(override val status: Int) : ResponseDifference()
