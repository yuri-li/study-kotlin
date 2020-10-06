package org.study

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.reflection.shouldBeSubtypeOf
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import java.lang.RuntimeException

fun divide(x: Int, y: Int): Int {
    if (y == 0) {
        throw RuntimeException("y cannot be 0")
    }
    return x / y
}

fun sayHello() {
    println("hello world")
}

class KotlinTypeSystemSpec : StringSpec({
    "null & immutable"{
//        val user = User(null)
        val user = User("001")
        //user.id = "002"
    }
    "nothing extends all-objects"{
        val exception = shouldThrow<RuntimeException> {
            divide(1, 0)
        }
        exception.message.shouldBe("y cannot be 0")
    }
    "unit type"{
        val sayHello = sayHello()
        sayHello.shouldBeTypeOf<Unit>()
    }
    "classes & inheritance"{
        val pig: Animal = Pig() //val nothing:Int = Nothing()
        val monkey: Monkey = Monkey()

        pig.shouldBeTypeOf<Pig>()
        pig::class.shouldBeSubtypeOf<Animal>()
        monkey.shouldBeTypeOf<Monkey>()
        monkey::class.shouldBeSubtypeOf<Animal>()
    }
}) {
    data class User(
        val id: String
    )

    interface Animal
    data class Pig(val name: String = "pig") : Animal
    data class Monkey(val name: String = "monkey") : Animal
}