package com.github.kerubistan.kerub.planner

import com.github.kerubistan.kerub.common.blankState
import com.github.kerubistan.kerub.common.testDisk
import com.github.kerubistan.kerub.common.tinyState
import com.github.kerubistan.kerub.planner.reservations.VirtualStorageReservation
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.infra.Blackhole

open class OperationalStateBenchmark {
	@Benchmark
	fun copyTinyState(hole : Blackhole) {
		hole.consume(
				tinyState.copy(
						reservations = tinyState.reservations + VirtualStorageReservation(testDisk)
				)
		)
	}
	@Benchmark
	fun copyBlankState(hole : Blackhole) {
		hole.consume(
				blankState.copy(
						reservations = tinyState.reservations + VirtualStorageReservation(testDisk)
				)
		)
	}

}