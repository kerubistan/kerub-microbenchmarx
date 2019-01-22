package com.github.kerubistan.kerub.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kerubistan.kerub.benchmarkutils.random
import com.github.kerubistan.kerub.benchmarkutils.randomItem
import com.github.kerubistan.kerub.benchmarkutils.randomItems
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.stringify
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class SerializationBenchmark {

	val firstNames = listOf("John", "Bill", "George", "Barack", "Donald", "Hillary")
	val lastNames = listOf("Travolta", "Clinton", "Bush", "Obama", "Pump", "McCain")

	val titles = listOf("Sir", "Dr", "Saint", "Ninja")


	val people = (1..10000).map {
		Person(
				firstName = random.randomItem(firstNames),
				lastName = random.randomItem(lastNames),
				titles = random.randomItems(titles)
		)
	}

	val mapper = ObjectMapper()

	val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
	val jsonAdapter = moshi.adapter(Person::class.java)

	@Benchmark
	fun serializeWithMoshi(blackHole :Blackhole) {
		people.forEach {
			blackHole.consume(jsonAdapter.toJson(it))
		}
	}

	@Benchmark
	fun serializeWithJackson(blackHole :Blackhole) {
		people.forEach {
			blackHole.consume(mapper.writeValueAsString(it))
		}
	}

	@UseExperimental(ImplicitReflectionSerializer::class)
	@Benchmark
	fun serializeWithKotlinSer(blackHole :Blackhole) {
		people.forEach {
			blackHole.consume(JSON.stringify(it))
		}
	}

}