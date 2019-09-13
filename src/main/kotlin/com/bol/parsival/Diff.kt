package com.bol.parsival

class Diff<K, V>(
    private val old: Map<K, V>,
    private val new: Map<K, V>,
    private val mapAdded: ((K) -> Difference)? = null,
    private val mapRemoved: ((K) -> Difference)? = null,
    private val mapChanged: ((K, V, V) -> Difference)? = null
) {
    fun differences(): List<Difference> {
        return added().map { mapAdded?.invoke(it) }
            .plus(removed().map { mapRemoved?.invoke(it) })
            .plus(changed().map { key ->
                mapChanged?.invoke(key, old.getValue(key), new.getValue(key))
            })
            .filterNotNull()
    }

    fun removed() = old.keys.minus(new.keys)

    fun added() = new.keys.minus(old.keys)

    fun changed() = new.keys.intersect(old.keys)

    data class Builder<K, V>(
        var old: Map<K, V>,
        var new: Map<K, V>,
        var mapAdded: ((K) -> Difference)? = null,
        var mapRemoved: ((K) -> Difference)? = null,
        var diffOnKey: ((K, V, V) -> Difference)? = null
    ) {
        fun mapAdded(block: (K) -> Difference) = apply { this.mapAdded = block }
        fun mapRemoved(block: (K) -> Difference) = apply { this.mapRemoved = block }
        fun mapChanged(block: (K, V, V) -> Difference) = apply { this.diffOnKey = block }

        fun build() = Diff(old, new, mapAdded, mapRemoved, diffOnKey)
    }
}

fun <K, V> diffOf(old: Map<K, V>, new: Map<K, V>): Diff.Builder<K, V> {
    return Diff.Builder(old, new)
}
