package com.github.kerubistan.kerub.utils

import io.github.kerubistan.kroki.collections.join
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
open class ListUtilsBenchmark {

	var lists = listOf(listOf(1,2,3,4,5), listOf(7,8,9), listOf(), listOf(10,11), listOf(12))
	var result : Any? = null

	@Benchmark
	fun join() {
		result = lists.join()
	}
}