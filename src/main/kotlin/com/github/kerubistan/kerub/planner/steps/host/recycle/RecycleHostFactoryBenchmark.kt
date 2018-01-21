package com.github.kerubistan.kerub.planner.steps.host.recycle

import com.github.kerubistan.kerub.common.tinyState
import com.github.kerubistan.kerub.planner.steps.AbstractOperationalStep
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
open class RecycleHostFactoryBenchmark {

	var noRecycle = tinyState
	var steps : List<AbstractOperationalStep>? = null

	@Benchmark
	fun produce() {
		steps = RecycleHostFactory.produce(noRecycle)
	}

}