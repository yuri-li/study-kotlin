package org.study

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import java.time.LocalDate


class KotlinFileSpec : StringSpec({
    "test my first function"{
        sum(1, 1).shouldBe(2)
    }
    "define variables"{
        val i = 1
        i.shouldBeTypeOf<Int>()
    }
    "data class"{
        val user = User("001", 18, true, LocalDate.of(2002, 7, 8))

        user.id.shouldBe("001")
        user.gender.shouldBeTrue()
        user.birthday.toString().shouldBe("2002-07-08")

        user.toString()
            .shouldBe("User(id=001, age=18, gender=true, birthday=2002-07-08)")

        (user == user.copy()).shouldBeTrue()
        (user == user.copy(id = "002")).shouldBeFalse()

        user.toString()
            .shouldBe("User(id=001, age=18, gender=true, birthday=2002-07-08)")
    }
}) {
    data class User(
        val id: String,
        val age: Int,
        val gender: Boolean,
        val birthday: LocalDate
    )

    companion object {
        fun sum(x: Int, y: Int) = x + y
    }
}