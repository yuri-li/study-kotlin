---
typora-root-url: ./assets
---

- Coroutine Basics
  - foundational concepts
  - suspending functions
- Coroutine  Builders
  - launch
  - async
  - runblocking

# 1 coroutine是什么

`coroutine`在kotlin中，特指`官方提供的一套线程API`。优点：

- 基于kotlin的语言优势，代码简洁
- 可以用`看起来`同步的代码，实现异步与回调的效果。即：`非阻塞式挂起`

## 1.1 为什么coroutine不会阻塞线程？

占用资源较多的函数，请先使用`suspend`关键字标记

```
suspend fun loadImageRemotely(){
...
}
```

### 1.1.1 `suspend`有什么用

- 标记耗时较长的函数
- 若正确调用了协程，那么，`suspend`标记的函数不会阻塞主线程
  - 如果是我写的实现，使用单元测试，验证是否会阻塞主线程
  - 别人写的，同样需要验证

最终，形成一套`机制`，让所有耗时任务，都自动转到后台执行，执行完毕后，再**<u>自动切换回来</u>**。

suspend只是一个标记，什么都没做。真正实现`非阻塞式挂起`的，是函数里的代码。

反模式，eg.

![Screen Shot 2020-10-13 at 11.15.38 PM](/Screen%20Shot%202020-10-13%20at%2011.15.38%20PM.png)

### 1.1.2 怎么实现自动切换？

设当前线程为A，其他线程为S。假设A的任务耗时较长，则，

- 将A转入后台运行。同时，切换到线程S

- A执行完毕后，通知主线程

### 1.1.3 思考

挂起后，线程A是否依然在阻塞执行？

## 1.2 练习题

使用协程实现一个网络请求（简单模拟）

- 等待5秒，然后，提示加载成功

- 等待时显示 Loading
- 请求成功让 Loading 消失
- 让你的代码看上去像单线程

## 1.3 coroutine是轻量级线程吗？

> 不是

用`Java`的`Executors`也可以使用相同的功能，只是代码稍微多点

```
import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class LotsOfCoroutinesSpec : StringSpec({
    "test the kotlin official example"{// 5s 548ms
        repeat(100_000) {
            launch {
                delay(5000L)
                print(".")
            }
        }
    }
    "test the example of Java version"{// 5s 291ms
        val executor = Executors.newSingleThreadScheduledExecutor()
        val task = Runnable {
            print(".")
        }
        repeat(100_000) {
            executor.schedule(task, 5L, TimeUnit.SECONDS)
        }
        executor.shutdown()
        executor.awaitTermination(10L, TimeUnit.SECONDS)
    }
})
```

