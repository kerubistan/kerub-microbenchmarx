package com.github.kerubistan.kerub.kotlin

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class MapNullBenchmark {

	val filterDomain = 'A'..'Z'
	var dataWithSomeMatch = listOf('A', '1', 'B', 'C', 'D', '2', 'E', '3')
	var dataWithAllMatch = listOf('A', 'B', 'C', 'D', 'E')

	@Benchmark
	fun streamSomeSmall(hole: Blackhole) {
		hole.consume(
				dataWithSomeMatch.asSequence()
						.filter { it in filterDomain }
						.map { it.toLowerCase() }
						.toList()
		)
	}

	@Benchmark
	fun streamAllMatch(hole: Blackhole) {
		hole.consume(
				dataWithAllMatch.asSequence()
						.filter { it in filterDomain }
						.map { it.toLowerCase() }
						.toList()
		)
	}

	@Benchmark
	fun mapAndFilterSomeSmall(hole: Blackhole) {
		hole.consume(
				dataWithSomeMatch.map {
					if (it in filterDomain) {
						it.toLowerCase()
					} else null
				}.filterNotNull()
		)
	}

	@Benchmark
	fun mapAndFilterAllCaps(hole: Blackhole) {
		hole.consume(
				dataWithAllMatch.map {
					if (it in filterDomain) {
						it.toLowerCase()
					} else null
				}.filterNotNull()
		)
	}


	@Benchmark
	fun mapNotNullSomeSmall(hole: Blackhole) {
		hole.consume(
				dataWithSomeMatch.mapNotNull {
					if (it in filterDomain) {
						it.toLowerCase()
					} else null
				}
		)
	}

	@Benchmark
	fun mapNotMullAllCaps(hole: Blackhole) {
		hole.consume(
				dataWithAllMatch.mapNotNull {
					if (it in filterDomain) {
						it.toLowerCase()
					} else null
				}
		)
	}


	@Benchmark
	fun filterAndMapSomeSmall(hole: Blackhole) {
		hole.consume(
				dataWithSomeMatch.filter { it in filterDomain }.map { it.toLowerCase() }
		)
	}

	@Benchmark
	fun filterAndMapAllCaps(hole: Blackhole) {
		hole.consume(
				dataWithAllMatch.filter { it in filterDomain }.map { it.toLowerCase() }
		)
	}


}