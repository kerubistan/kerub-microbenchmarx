package com.github.kerubistan.kerub.utils.maps

class UpdatedMap<K, V>(val original: Map<K, V>, val key: K, val value: V) : Map<K, V> {

	override val entries: Set<Map.Entry<K, V>>
		get() = original.entries + MapEntry(key, value)
	override val keys: Set<K>
		get() = original.keys + key
	override val size: Int
		get() = if(original.containsKey(key)) original.size else original.size + 1
	override val values: Collection<V>
		get() = TODO("not implemented")

	override fun containsKey(key: K): Boolean = (key == this.key) || original.containsKey(key)

	override fun containsValue(value: V): Boolean = (value == value) || original.containsValue(value)

	override fun get(key: K): V? = if (key == this.key) {
		value
	} else {
		original[key]
	}

	override fun isEmpty(): Boolean = false
}