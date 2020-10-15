package org.study.creational

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class FactorySpec : StringSpec({
    "test simple factory"{
        ShapeFactory.buildShape(ShapeEnum.Rectangle).draw().shouldBe("Rectangle")
    }
}) {
    enum class ShapeEnum {
        Circle, Square, Rectangle
    }

    interface Shape {
        fun draw(): String
    }

    class Circle : Shape {
        override fun draw(): String = ShapeEnum.Circle.name
    }

    class Square : Shape {
        override fun draw(): String = ShapeEnum.Square.name
    }

    class Rectangle : Shape {
        override fun draw(): String = ShapeEnum.Rectangle.name
    }

    object ShapeFactory {
        fun buildShape(shapeEnum: ShapeEnum): Shape = when (shapeEnum) {
            ShapeEnum.Circle -> Circle()
            ShapeEnum.Square -> Square()
            ShapeEnum.Rectangle -> Rectangle()
        }
    }
}