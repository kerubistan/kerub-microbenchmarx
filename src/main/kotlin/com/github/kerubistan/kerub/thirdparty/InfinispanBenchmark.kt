package com.github.kerubistan.kerub.thirdparty

import com.github.kerubistan.kerub.benchmarkutils.randomItem
import org.infinispan.Cache
import org.infinispan.manager.DefaultCacheManager
import org.infinispan.manager.EmbeddedCacheManager
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.TearDown
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole
import java.io.Serializable
import java.util.Random
import java.util.UUID

@State(Scope.Benchmark)
@Fork(5)
@Warmup(iterations = 10)
open class InfinispanBenchmark {

	@Param("local-cache", "local-cache-persistent"/*, "replicated-cache", "distributed-cache"*/)
	public var cacheType = "local"

	@Param(
			"1",
			"2",
			"4",
			"8",
			"16",
			"32",
			"64",
			"128",
			"256",
			"512",
			"1024",
			"2048",
			"4096",
			"8192",
			"16384",
			"32768",
			"65536",
			"131072")
	public var size = 0

	enum class Status {
		Sleeping,
		Working,
		Eating,
		Dead
	}

	var firstSomeIds = setOf<UUID>()

	data class Person(
			val id : UUID,
			val firstName : String,
			val lastName : String,
			val title : String,
			val status : Status
	) : Serializable

	val titles = listOf("mr","dr","ms","mrs","phd","sir","junior","st")

	val random = Random()

	var cache : Cache<UUID, Person>? = null
	var cacheManager : EmbeddedCacheManager? = null

	var newPerson = Person(
			id = UUID.randomUUID(),
			lastName = "Kakukk",
			firstName = "Jeno",
			status = Status.Eating,
			title = ""
	)

	var firstPersonId = UUID.randomUUID()
	var nonexistentPersonId = UUID.randomUUID()

	@Setup
	fun setup() {

		cacheManager = DefaultCacheManager("infinispan-benchmark.xml")
		cache = cacheManager!!.getCache<UUID, Person>()

		(1..size).forEach {
			val person = Person(
				id = UUID.randomUUID(),
					title = random.randomItem(titles),
					status = Status.Working,
					firstName = "firstname-$it",
					lastName = "lastname-$it"
			)
			cache!![person.id] = person
			if(it < 8) {
				firstSomeIds = firstSomeIds + person.id
			}
		}
	}

	@TearDown
	fun cleanup() {
		cache?.clear()
		cache?.stop()
		cacheManager!!.stop()
	}

	@Benchmark()
	fun put(hole: Blackhole) {
		cache!![newPerson.id] = newPerson
	}

	@Benchmark
	fun get(hole: Blackhole) {
		hole.consume(
				cache!![firstPersonId]
		)
	}

	@Benchmark
	fun getSome(hole: Blackhole) {
		hole.consume(
				cache!!.advancedCache.getAll(firstSomeIds)
		)
	}

	@Benchmark
	fun getAndMiss(hole: Blackhole) {
		hole.consume(
				cache!![nonexistentPersonId]
		)
	}

}