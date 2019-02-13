package com.github.kerubistan.kerub.utils.collections

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class InListVsSetBenchmark {
	var list = ('A'..'Z').map { it.toString() }.toList()
	var set = list.toSet()

	@Benchmark
	fun inListA(hole : Blackhole) {
		hole.consume("A" in list)
	}

	@Benchmark
	fun inListZ(hole : Blackhole) {
		hole.consume("Z" in list)
	}

	@Benchmark
	fun inSetA(hole : Blackhole) {
		hole.consume("A" in set)
	}

	@Benchmark
	fun inSetZ(hole : Blackhole) {
		hole.consume("Z" in set)
	}

	@Benchmark
	fun inListMiss(hole: Blackhole) {
		hole.consume("9" in list)
	}

	@Benchmark
	fun inSetMiss(hole: Blackhole) {
		hole.consume("9" in set)
	}

}