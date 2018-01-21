package com.github.kerubistan.kerub.planner.steps

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
open class UtilsBenchmark {

	private var enabled = true
	private var producer = { listOf<String>() }

	@Benchmark
	fun factoryFeatureWithEnabled() {
		factoryFeature(enabled, producer)
	}
}