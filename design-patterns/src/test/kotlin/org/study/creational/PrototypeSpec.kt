package org.study.creational

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PrototypeSpec : StringSpec({
    "test prototype pattern"{
        val teachers = listOf(
            Teacher("zhangsan"),
            Teacher("lisi"),
            Teacher("wangwu"),
        )
        val prototype = Student("xiaoming", teachers)
        val copy = prototype.copy()

        copy.teachers.size.shouldBe(prototype.teachers.size)

        val newStudent = prototype.copy(teachers = prototype.teachers.map { Teacher(it.name.toUpperCase()) })
        newStudent.teachers[0].name.shouldBe("ZHANGSAN")

        val student2 = prototype.copy(name = "xiaohong")
        prototype.name.shouldBe("xiaoming")
        student2.name.shouldBe("xiaohong")
        student2.teachers[0].name.shouldBe("zhangsan")
    }
}) {
    data class Student(val name: String, val teachers: List<Teacher>)
    data class Teacher(val name: String)
}