package com.github.kerubistan.kerub.planner.steps.host.security.remove

import com.github.kerubistan.kerub.planner.OperationalState
import com.github.kerubistan.kerub.planner.scenarios.withHostPubKeys
import com.github.kerubistan.kerub.planner.scenarios.withHostPubKeysInstalled
import com.github.kerubistan.kerub.planner.scenarios.withHosts
import com.github.kerubistan.kerub.planner.scenarios.withHostsUp
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class RemovePublicKeyFactoryBenchmark {

	var blankState = OperationalState.fromLists()

	var tinyState = OperationalState.fromLists()
			.withHosts(16)
			.withHostsUp(1..12)
			.withHostPubKeys(1..8)
			.withHostPubKeysInstalled(1..4, 8..16)

	var smallState = OperationalState.fromLists()
			.withHosts(128)
			.withHostsUp(1..64)
			.withHostPubKeys(1..48)
			.withHostPubKeysInstalled(32..64, 8..16)

	@Benchmark
	fun runBlank(hole: Blackhole) {
		hole.consume(RemovePublicKeyFactory.produce(blankState))
	}

	@Benchmark
	fun runTiny(hole: Blackhole) {
		hole.consume(RemovePublicKeyFactory.produce(tinyState))
	}

	@Benchmark
	fun runSmall(hole: Blackhole) {
		hole.consume(RemovePublicKeyFactory.produce(smallState))
	}

}