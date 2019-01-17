package com.github.kerubistan.kerub.utils

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class MapIterationBenchmark {

	val map = (1..100).associateBy { it.toString() }.inverse()

	@Benchmark
	fun iterateOnValues(hole: Blackhole) {
		map.values.forEach { hole.consume(it) }
	}

	@Benchmark
	fun iterateOnItems(hole: Blackhole) {
		map.forEach { hole.consume(it.value) }
	}

}