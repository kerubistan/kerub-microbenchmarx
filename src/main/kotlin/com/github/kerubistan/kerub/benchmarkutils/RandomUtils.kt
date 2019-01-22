package com.github.kerubistan.kerub.benchmarkutils

import java.util.Random

fun <T> Random.randomItem(items: List<T>): T = items[this.nextInt(items.size - 1)]

fun <T> Random.randomItems(items: List<T>): List<T> = (1..this.nextInt(items.size))
		.map { items[this.nextInt(items.size - 1)] }.toSet().toList()

val random = Random()
