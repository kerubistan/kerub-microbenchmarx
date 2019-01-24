package com.github.kerubistan.kerub.utils

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

/**
 * This is kind of important for kerub, it does this a lot when taking steps the planner.
 */
@State(Scope.Benchmark)
open class MapUpdateBenchmark {

	private fun <K : Any, V : Any> Map<K, V>.mapUpdate(key: K, mapper: (V) -> V): Map<K, V> =
			this.mapValues {
				if(it.key == key) {
					mapper(it.value)
				} else {
					it.value
				}
			}

	private inline fun <K : Any, V : Any> Map<K, V>.inlineMapUpdate(key: K, mapper: (V) -> V): Map<K, V> =
			this.mapValues {
				if(it.key == key) {
					mapper(it.value)
				} else {
					it.value
				}
			}

	private fun <K : Any, V : Any> Map<K, V>.update(key: K, mapper: (V) -> V): Map<K, V> =
			this + (key to mapper(requireNotNull(this[key])))

	private inline fun <K : Any, V : Any> Map<K, V>.inlineUpdate(key: K, mapper: (V) -> V): Map<K, V> =
			this + (key to mapper(requireNotNull(this[key])))

	val map = mapOf(
			"foo" to "bar",
			"x" to "y",
			"alpha" to "omega",
			"han" to "chubakka",
			"a" to "z",
			"batman" to "robin",
			"0" to "9"
	)

	val bigMap = (1..100000).map { it to "value-$it" }.toMap()


	@Benchmark
	fun mapUpdate(hole : Blackhole) {
		hole.consume(map.mapUpdate("foo") { "$it-updated" })
	}

	@Benchmark
	fun inlineMapUpdate(hole : Blackhole) {
		hole.consume(map.inlineMapUpdate("foo") { "$it-updated" })
	}

	@Benchmark
	fun inlineUpdate(hole : Blackhole) {
		hole.consume(map.inlineUpdate("foo") { "$it-updated" })
	}

	@Benchmark
	fun nonInlineUpdate(hole : Blackhole) {
		hole.consume(map.update("foo") { "$it-updated" })
	}

	@Benchmark
	fun bigMapUpdate(hole : Blackhole) {
		hole.consume(bigMap.mapUpdate(512) { "$it-updated" })
	}

	@Benchmark
	fun biInlinegMapUpdate(hole : Blackhole) {
		hole.consume(bigMap.inlineMapUpdate(512) { "$it-updated" })
	}

	@Benchmark
	fun bigInlineUpdate(hole : Blackhole) {
		hole.consume(bigMap.inlineUpdate(512) { "$it-updated" })
	}

	@Benchmark
	fun bigNonInlineUpdate(hole : Blackhole) {
		hole.consume(bigMap.update(512) { "$it-updated" })
	}

}