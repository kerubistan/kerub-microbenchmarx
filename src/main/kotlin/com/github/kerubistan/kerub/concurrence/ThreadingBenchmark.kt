package com.github.kerubistan.kerub.concurrence

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import nl.komponents.kovenant.task
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole
import kotlin.concurrent.thread

@State(Scope.Benchmark)
open class ThreadingBenchmark {

	@Benchmark
	fun runWithoutThreads(hole: Blackhole) {
		hole.consume(1)
	}

	@Benchmark
	fun runWithThreads(hole: Blackhole) {
		thread { hole.consume(1) }.join()
	}

	@Benchmark
	fun runTask(hole: Blackhole) {
		hole.consume(task { 1 }.get())
	}

	@Benchmark
	fun runCoroutine(hole: Blackhole) {
		val deferred = GlobalScope.async { 1 }
		runBlocking { hole.consume(deferred.await()) }
	}

}