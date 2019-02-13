package com.github.kerubistan.kerub.kotlin.iteration

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class IterationBenchmark {

	@Param("0","1","10","20","30","40","50")
	public var rangeTo = 0

	var range = 1..100

	@Setup
	fun setup() {
		range = 0..rangeTo
	}

	@Benchmark
	fun forOnRange(hole : Blackhole) {
		for(i in range) {
			hole.consume(i)
		}
	}

	@Benchmark
	fun rangeForEach(hole : Blackhole) {
		range.forEach {
			hole.consume(it)
		}
	}

	@Benchmark
	fun counting(hole :Blackhole) {
		var cntr = 0
		while (cntr < rangeTo) {
			hole.consume(cntr)
			cntr++
		}
	}

}