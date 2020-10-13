package org.study

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

infix fun String.eq(value:String) = "${this} = '${value}'"
class FunSpec : StringSpec({
    "test default arguments"{
        sum(1).shouldBe(2)
        sum(1, 2).shouldBe(3)
    }
    "test default arguments & named arguments"{
        minus(y = 10, x = 2).shouldBe(-8)
        minus(10).shouldBe(9)
        minus(10, 2).shouldBe(8)
    }
    "test vararg"{
        addAll(1,2,3,4,5).shouldBe(15)
        addAll().shouldBe(0)
    }
    "test infix"{
        ("id" eq "001").shouldBe("id = '001'")
    }
}) {
    companion object {
        fun sum(x: Int, y: Int = 1) = x + y
        fun minus(x: Int, y: Int = 1) = x - y
        fun addAll(vararg x: Int) = x.sum()
    }
}