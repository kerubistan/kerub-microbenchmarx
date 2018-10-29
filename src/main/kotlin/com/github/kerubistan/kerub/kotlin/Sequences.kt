package com.github.kerubistan.kerub.kotlin

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class Sequences {

	val smallList = createList().shuffled()

	fun createList() = (1..5000).toList() + (1..5000).toList().map { it.toString() }

	@Benchmark
	fun processSingleStep(blackhole: Blackhole) {
		blackhole.consume(smallList.filterIsInstance<Number>())
	}

	@Benchmark
	fun processTwoSteps(blackhole: Blackhole) {
		blackhole.consume(
				smallList.filterIsInstance<Number>()
						.filter {
							it.toInt() > 1
						}
		)
	}

	@Benchmark
	fun processTwoStepsSequence(blackhole: Blackhole) {
		blackhole.consume(
				smallList.asSequence().filterIsInstance<Number>()
						.filter {
							it.toInt() > 1
						}.toList()
		)
	}

	@Benchmark
	fun processThreeSteps(blackhole: Blackhole) {
		blackhole.consume(
				smallList.filterIsInstance<Number>()
						.filter {
							it.toInt() > 1
						}.map { it.toInt() + 1 }
		)
	}

	@Benchmark
	fun processThreeStepsSequence(blackhole: Blackhole) {
		blackhole.consume(
				smallList.asSequence().filterIsInstance<Number>()
						.filter {
							it.toInt() > 1
						}.map { it.toInt() + 1 }.toList()
		)
	}

	@Benchmark
	fun processFourSteps(blackhole: Blackhole) {
		blackhole.consume(
				smallList.filterIsInstance<Number>()
						.filter {
							it.toInt() > 1
						}.map { it.toInt() + 1 }
						.filter { it % 2 == 0 }
		)
	}

	@Benchmark
	fun processFourStepsSequence(blackhole: Blackhole) {
		blackhole.consume(
				smallList.asSequence().filterIsInstance<Number>()
						.filter {
							it.toInt() > 1
						}.map { it.toInt() + 1 }
						.filter { it % 2 == 0 }
						.toList()
		)
	}

	@Benchmark
	fun processSum() {
		smallList
				.filterIsInstance<Int>()
				.sum()
	}

	@Benchmark
	fun processSumSequence() {

		smallList
				.asSequence()
				.filterIsInstance<Int>()
				.sum()
	}

}