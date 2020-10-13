package org.study

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ObjectSpec : StringSpec({
    "test Anonymous inner class"{
        val list = listOf<Animal>(
            object : Animal {
                override fun getName(): String = "pig"
            },
            object : Animal {
                override fun getName(): String = "monkey"
            },
        )
        val i:Int = 0
        list.size.shouldBe(2)
        list[0].getName().shouldBe("pig")
        list[1].getName().shouldBe("monkey")
    }
}) {
    interface Animal {
        fun getName(): String
    }
}