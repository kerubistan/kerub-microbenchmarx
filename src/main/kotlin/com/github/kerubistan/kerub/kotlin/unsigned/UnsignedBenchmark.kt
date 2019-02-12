package com.github.kerubistan.kerub.kotlin.unsigned

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class UnsignedBenchmark {

	var signed = 12.toLong()
	@ExperimentalUnsignedTypes
	var unsigned = 12.toULong()

	@ExperimentalUnsignedTypes
	@Benchmark
	fun unsignedIncrement(hole : Blackhole) {
		hole.consume(unsigned.inc())
	}

	@Benchmark
	fun signedIncrement(hole : Blackhole) {
		hole.consume(signed.inc())
	}

	@Benchmark
	fun signedComparison(hole : Blackhole) {
		hole.consume(signed < 13)
	}

	@ExperimentalUnsignedTypes
	@Benchmark
	fun unsignedComparison(hole : Blackhole) {
		hole.consume(unsigned < 13.toULong())
	}

}