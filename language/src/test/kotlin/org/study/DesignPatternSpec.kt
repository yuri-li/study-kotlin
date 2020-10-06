package org.study

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import java.util.*

class DesignPatternSpec : StringSpec({
    "singleton"{
        val firstId = User.id
        val secondId = User.id

        firstId.shouldBe(secondId)
    }
    "prototype"{
        val teacher = Teacher(age = 18, id = "001")

        (teacher.copy() == teacher).shouldBeTrue()
    }
}) {
    object User {
        val id: String = UUID.randomUUID().toString()
    }

    data class Teacher(
        val id: String,
        val age: Int
    )
}