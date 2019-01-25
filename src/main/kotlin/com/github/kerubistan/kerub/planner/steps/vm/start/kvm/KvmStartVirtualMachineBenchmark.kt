package com.github.kerubistan.kerub.planner.steps.vm.start.kvm

import com.github.kerubistan.kerub.common.testVm
import com.github.kerubistan.kerub.planner.OperationalState
import com.github.kerubistan.kerub.planner.scenarios.withHosts
import com.github.kerubistan.kerub.planner.scenarios.withHostsUp
import com.github.kerubistan.kerub.planner.scenarios.withRunningWm
import com.github.kerubistan.kerub.planner.scenarios.withWms
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class KvmStartVirtualMachineBenchmark {

	val tinyState = OperationalState.fromLists()
			.withHosts(16)
			.withHostsUp(1..12)
			.withWms(128)
			.withRunningWm(testVm)

	val smallState = OperationalState.fromLists()
			.withHosts(128)
			.withHostsUp(1..64)
			.withWms(1024)
			.withRunningWm(testVm)

	val tinyStep = KvmStartVirtualMachine(
			host = tinyState.runningHosts.first().stat,
			vm = testVm
	)

	val smallStep = KvmStartVirtualMachine(
			host = smallState.runningHosts.first().stat,
			vm = testVm
	)

	@Benchmark
	fun updateTinyState(hole : Blackhole) {
		hole.consume(tinyStep.take(tinyState))
	}
	@Benchmark
	fun updateSmallState(hole : Blackhole) {
		hole.consume(smallStep.take(smallState))
	}
}