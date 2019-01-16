package com.github.kerubistan.kerub.serialization

import kotlinx.serialization.Serializable

@Serializable
data class Person(
		val firstName : String,
		val lastName : String,
		val titles : List<String> = listOf()
)