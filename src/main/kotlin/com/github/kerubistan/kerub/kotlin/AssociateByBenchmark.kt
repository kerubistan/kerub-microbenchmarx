package com.github.kerubistan.kerub.kotlin

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class AssociateByBenchmark {

	data class Employee(
			val id : Long,
			val name : String
	)

	var employees = listOf(
			Employee(1, "John"),
			Employee(2, "Jane"),
			Employee(3, "Bob"),
			Employee(4, "Chris"),
			Employee(5, "Sue"),
			Employee(6, "Jef"),
			Employee(7, "Joe"),
			Employee(8, "Whatever")

	)

	@Benchmark
	fun associateBy(hole : Blackhole) {
		hole.consume(employees.associateBy { it.id })
	}

	@Benchmark
	fun listPairsToMap(hole: Blackhole) {
		hole.consume(employees.map { it.id to it }.toMap())
	}

}