package org.study.structual

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


class DecoratorSpec : StringSpec({
    "no wrap"{
        val withMilk = WithMilk()
        withMilk.cost().shouldBe(0.5)
        withMilk.ingredients().shouldBe("Milk")
    }
    "wrap one level"{
        val coffee = object : Coffee {}

        val withMilk = WithMilk(coffee)
        withMilk.cost().shouldBe(1.5)
        withMilk.ingredients().shouldBe("Coffee, Milk")

        val withSprinkles = WithSprinkles(coffee)
        withSprinkles.cost().shouldBe(1.2)
        withSprinkles.ingredients().shouldBe("Coffee, Sprinkles")
    }
    "test list.joinToString"{
        val list = listOf(1, 2, 3)
        list.joinToString(", ").shouldBe("1, 2, 3")
    }
}) {
    interface Coffee {
        fun cost() = 1.0
        fun ingredients() = "Coffee"
    }

    data class Ingredient(
        val cost: Double,
        val name: String
    )

    abstract class CoffeeDecorator(open val decoratedCoffee: Coffee? = null) : Coffee {
        override fun cost() = decoratedCoffee?.cost() ?: 0.0
        fun buildIngredients(currentIngredients: String) =
            listOf(decoratedCoffee?.ingredients() ?: "", currentIngredients).filterNot { it == "" }.joinToString(", ")
    }

    class WithMilk(override val decoratedCoffee: Coffee? = null) : CoffeeDecorator(decoratedCoffee) {
        override fun cost() = super.cost() + 0.5
        override fun ingredients() = buildIngredients("Milk")
    }

    class WithSprinkles(override val decoratedCoffee: Coffee? = null) : CoffeeDecorator(decoratedCoffee) {
        override fun cost() = super.cost() + 0.2
        override fun ingredients() = buildIngredients("Sprinkles")
    }
}