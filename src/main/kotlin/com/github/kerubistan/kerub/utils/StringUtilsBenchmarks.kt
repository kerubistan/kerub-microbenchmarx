package com.github.kerubistan.kerub.utils

import io.github.kerubistan.kroki.strings.substringBetween
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
open class StringUtilsBenchmarks {

	var testString = "some text BEGIN some other text END and then on and on"

	@Benchmark
	fun substringBetween() {
		testString.substringBetween("BEGIN", "END")
	}
}