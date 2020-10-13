---
typora-root-url: ./assets
---

# 1 从Java到kotlin

- 开发框架

  作为Java程序员，做web开发，肯定会选择spring框架。目前，性能最好的开发框架是`spring webflux`，这种`reactor`模式的开发思想，需要对多线程、流、事件驱动等机制有详细的了解。而这些理念跟`面向对象`的关系其实不大，反而是比较古老的`function`思想，更契合。

  如果继续使用纯粹的面向对象的思想编程，会很痛苦。

  Java8的语法也进一步坐实了这个观点！不需要狡辩，代码写出来，一眼扫过去就知道哪一种更适合人类阅读。跟你对语法和API的熟练度，一点关系都没有，纯粹就是理念层面的问题。Java已经做得很好了，但是，为了兼容老版本，不得不妥协，包袱太重了。

- 项目构建/运维

  项目构建的工具很多：ant/maven/gradle，都是很流行的工具。当然，选择gradle也是因为使用其他两个工具遇到BUG了，也是理念层面的问题，所以，不得不换用gradle

  gradle的脚本，早期使用groovy写的，现在呢，可以使用kotlin写了。可以少学一门语言，所以呢，推荐使用kotlin编写gradle的脚本。而且，经过3年多的摸索，还没有遇到解决不了的问题

- 测试

  测试也是代码。开发能少写代码，同样的，测试也能少写很多代码

# 2 kotest 插件

[IntelliJ idea plugin](https://kotest.io/intellij/)

# 3 Basic Syntax

## 3.1 kotlin file

- 文件后缀名`.kt`
- 编译后的文件名`.class`
- 一个kotlin file可以包含多个`class,function`
- 在kotlin中，`function`和`class`是平级的概念

<img src="/image-20201003161347132.png" alt="image-20201003161347132" style="zoom:50%;" />

```
package org.study

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import java.time.LocalDate

fun sum(x: Int, y: Int) = x + y
data class User(
    val id: String,
    val age: Int,
    val gender: Boolean,
    val birthday: LocalDate
)

class KotlinFileSpec : StringSpec({
    "test my first function: `sum`"{
        sum(1, 1).shouldBe(2)
    }
    "define variables"{
        val i = 1
        i.shouldBe(1)
        i.shouldBeTypeOf<Int>()
    }
    "data class"{
        val user = User("001", 18, true, LocalDate.of(2002,7,8))

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
})
```

## 3.2 kotlin type system

![image-20201004172648693](/image-20201004172648693.png)

### 3.2.1 Null and immutable

```
data class User(
    val id: String
)
"null & immutable"{
    //val user = User(null) //val id: String?
    val user = User("001")
    //user.id = "002" //var id: String
}
```

### 3.2.2 Nothing

可以把`Nothing`看作异常

```
fun divide(x:Int,y:Int):Int{
    if(y == 0){
        throw RuntimeException("y cannot be 0")
    }
    return x/y
}

"nothing extends all-objects"{
    val exception = shouldThrow<RuntimeException> {
        divide(1,0)
    }
    exception.message.shouldBe("y cannot be 0")
}
```

### 3.2.3 Unit

```
fun sayHello() {
    println("hello world")
}
"unit type"{
    val sayHello = sayHello()
    sayHello.shouldBeTypeOf<Unit>()
}
```

### 3.2.4 classes and inheritance

```
interface Animal
data class Pig(val name: String = "pig") : Animal
data class Monkey(val name: String = "monkey") : Animal

"classes & inheritance"{
    val pig: Animal = Pig() //val nothing:Int = Nothing()
    val monkey: Monkey = Monkey()

    pig.shouldBeTypeOf<Pig>()
    pig::class.shouldBeSubtypeOf<Animal>()
    monkey.shouldBeTypeOf<Monkey>()
    monkey::class.shouldBeSubtypeOf<Animal>()
}
```

## 3.3 Design Pattern

```
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
```



# 4 creating multi-project builds

因为github是以project为单元，管理代码的。所以，为了将几个关联度比较紧密的项目放一起，需要一点小技巧。

- study-kotlin
  - language -- 记录学习kotlin语法的过程
  - testing -- 一些练习题
  - design-patterns -- 使用kotlin 推导设计模式

## 4.1 root project

```
1. 创建一个普通的kotlin项目
2. 指定gradle版本
withType<Wrapper> {
    gradleVersion = "6.6.1"
    distributionType = Wrapper.DistributionType.ALL
}
3. 执行wrapper（会替换gradle为build.gradle.kts中指定的版本）
4. 删除build.gradle.kts，src目录

5. 安装ignore-intellij idea插件
6. 重启intellij idea
7. 创建`.gitignore`文件
# Created by .ignore support plugin (hsz.mobi)
**/.gradle/
**/build/
**/.kotlintest/
**/logs/

### IntelliJ IDEA ###
**/.idea/
**/*.iws
**/*.iml
**/*.ipr
**/out/

**/src/main/**/build/
**/src/test/**/build/
**/src/main/**/out/
**/src/test/**/out/
```

## 4.2 other projects

在`study-kotlin`目录下，依次创建其他kotlin项目，且，修改gradle版本

## 4.3 settings.gradle.kts

```
rootProject.name = "study-kotlin"

include("language")
```

## 4.5 build

在`view --> tool windows --> gradle`(一般在屏幕最右侧)，选中项目名称，然后点击刷新按钮即可

![image-20201007090236902](/image-20201007090236902.png)



![image-20201007090327539](/image-20201007090327539.png)

# 5 version control

## 5.1 Share Project on GitHub



## 5.2 Import a project from version control



































