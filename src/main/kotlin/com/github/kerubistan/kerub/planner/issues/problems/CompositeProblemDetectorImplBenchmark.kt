package com.github.kerubistan.kerub.planner.issues.problems

import com.github.kerubistan.kerub.common.blankState
import com.github.kerubistan.kerub.common.tinyState
import com.github.kerubistan.kerub.planner.Plan
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
open class CompositeProblemDetectorImplBenchmark {

	var blankPlan = Plan(blankState)
	var results = listOf<Problem>()

	var tinyPlan = Plan(tinyState)

	@Benchmark
	fun detectWithBlankState() {
		CompositeProblemDetectorImpl.detect(blankPlan)
	}

	@Benchmark
	fun detectWithTinyState() {
		CompositeProblemDetectorImpl.detect(tinyPlan)
	}


}