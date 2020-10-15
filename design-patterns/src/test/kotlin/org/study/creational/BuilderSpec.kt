package org.study.creational

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BuilderSpec : StringSpec({
    "test builder pattern"{
        val person = Person(
            head = "head",
            body = "body",
            legs = "legs",
            arms = "arms"
        )
        person.legs.shouldBe("legs")
    }
}) {
    data class Person(val head: String, val arms: String, val legs: String, val body: String)
}