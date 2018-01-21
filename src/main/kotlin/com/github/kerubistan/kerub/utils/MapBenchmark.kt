package com.github.kerubistan.kerub.utils

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
open class MapBenchmark {

	val map = mapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five", 6 to "six", 7 to "seven",
					9 to "nine", 10 to "ten")

	@Benchmark
	fun mapUpdateWithFnLiteral() {
		map.update(key = 1,mapper = {"jedna"})
	}
}