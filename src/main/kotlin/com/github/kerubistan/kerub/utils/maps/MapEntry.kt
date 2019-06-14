package com.github.kerubistan.kerub.utils.maps

data class MapEntry<K, V>(override val key: K, override val value: V) : Map.Entry<K, V>