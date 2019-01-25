package com.github.kerubistan.kerub.planner.steps.vm.stop

import com.github.kerubistan.kerub.planner.OperationalState
import com.github.kerubistan.kerub.planner.scenarios.withHosts
import com.github.kerubistan.kerub.planner.scenarios.withHostsUp
import com.github.kerubistan.kerub.planner.scenarios.withVmsUp
import com.github.kerubistan.kerub.planner.scenarios.withWms
import com.github.kerubistan.kerub.planner.steps.vm.start.kvm.KvmStartVirtualMachine
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class StopVirtualMachineBenchmark {

	val tinyState = OperationalState.fromLists()
			.withHosts(16)
			.withHostsUp(1..12)
			.withWms(128)
			.withVmsUp(1..64)

	val smallState = OperationalState.fromLists()
			.withHosts(128)
			.withHostsUp(1..64)
			.withWms(1024)
			.withVmsUp(1..512)

	val tinyStep = StopVirtualMachine(
			host = tinyState.hosts[tinyState.runningVms.first().dynamic!!.hostId]!!.stat,
			vm = tinyState.runningVms.first().stat
	)

	val smallStep = KvmStartVirtualMachine(
			host = smallState.hosts[smallState.runningVms.first().dynamic!!.hostId]!!.stat,
			vm = smallState.runningVms.first().stat
	)

	@Benchmark
	fun updateTinyState(hole: Blackhole) {
		hole.consume(tinyStep.take(tinyState))
	}

	@Benchmark
	fun updateSmallState(hole: Blackhole) {
		hole.consume(smallStep.take(smallState))
	}


}