package com.github.kerubistan.kerub.planner.steps.host.security.generate

import com.github.kerubistan.kerub.planner.OperationalState
import com.github.kerubistan.kerub.planner.scenarios.withHostPubKeys
import com.github.kerubistan.kerub.planner.scenarios.withHosts
import com.github.kerubistan.kerub.planner.scenarios.withHostsUp
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class GenerateSshKeyFactoryBenchmark {

	var blankState = OperationalState.fromLists()

	var tinyState = OperationalState.fromLists()
			.withHosts(16)
			.withHostsUp(1..12)
			.withHostPubKeys(1..4)

	var smallState = OperationalState.fromLists()
			.withHosts(128)
			.withHostsUp(1..64)
			.withHostPubKeys(1..32)


	@Benchmark
	fun produceWithBlankState(hole: Blackhole) {
		hole.consume(
				GenerateSshKeyFactory.produce(blankState)
		)
	}

	@Benchmark
	fun produceWithTinyState(hole: Blackhole) {
		hole.consume(
				GenerateSshKeyFactory.produce(tinyState)
		)
	}

	@Benchmark
	fun produceWithSmallState(hole: Blackhole) {
		hole.consume(
				GenerateSshKeyFactory.produce(smallState)
		)
	}


}