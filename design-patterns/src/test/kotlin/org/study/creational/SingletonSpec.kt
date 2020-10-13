package org.study.creational

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.util.*
import kotlin.random.Random.Default.nextBoolean

class SingletonSpec : StringSpec({
    "initialize two objects"{
        val u1 = User()
        val u2 = User()

        u1.id.shouldNotBe(u2.id)
    }
    "test singleton"{
        Singleton.randomBoolean.shouldBe(Singleton.randomBoolean)
    }
}) {
    data class User(val id: String = UUID.randomUUID().toString())
    object Singleton{
        val randomBoolean = nextBoolean()
    }
}