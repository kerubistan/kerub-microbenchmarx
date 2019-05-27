package com.github.kerubistan.kerub.utils

import com.github.kerubistan.kerub.utils.maps.UpdatedMap
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class MapBenchmark {

	val map = mapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five", 6 to "six", 7 to "seven",
					9 to "nine", 10 to "ten")

	val biggerMap = mapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five", 6 to "six", 7 to "seven",
			9 to "nine", 10 to "ten", 11 to "eleven", 12 to "twelve", 13 to "thirteen", 14 to "fourteen",
			15 to "fifteen", 16 to "sixteen", 17 to "seventeen", 18 to "eighteen", 19 to "nineteen", 20 to "twenty")

	val biggerThin = UpdatedMap(original = biggerMap, key = 1, value = "jedna")

	@Benchmark
	fun findInSmall(hole : Blackhole) {
		hole.consume(map[1])
	}

	@Benchmark
	fun findInBig(hole : Blackhole) {
		hole.consume(biggerMap[1])
	}

	@Benchmark
	fun findInBigThin(hole : Blackhole) {
		hole.consume(biggerThin[1])
	}

	@Benchmark
	fun findInBigThinMiss(hole : Blackhole) {
		hole.consume(biggerThin[2])
	}

	@Benchmark
	fun mapUpdateWithFnLiteralSmall(hole : Blackhole) {
		hole.consume(map.update(key = 1,mapper = {"jedna"}))
	}

	@Benchmark
	fun mapUpdateSmall(hole : Blackhole) {
		hole.consume(map + (1 to "jedna"))
	}

	@Benchmark
	fun mapUpdateSmallThin(hole: Blackhole) {
		hole.consume(UpdatedMap(map, 1, "jedna"))
	}

	@Benchmark
	fun mapUpdateWithFnLiteralBig(hole : Blackhole) {
		hole.consume(biggerMap.update(key = 1,mapper = {"jedna"}))
	}

	@Benchmark
	fun mapUpdateBig(hole : Blackhole) {
		hole.consume(biggerMap+ (1 to "jedna"))
	}

}