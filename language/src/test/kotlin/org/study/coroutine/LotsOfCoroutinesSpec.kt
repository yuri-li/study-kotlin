package org.study.coroutine

import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class LotsOfCoroutinesSpec : StringSpec({
    "test the kotlin official example"{// 1s 566ms
        val atomic = AtomicInteger()
        launch {
            repeat(100_000) {
                launch {
                    delay(1000L)
                    print(".${it}")
                    atomic.getAndIncrement()
                }
            }
        }.invokeOnCompletion {
            println("=============")
            println("--${atomic.get()}--")
        }
    }
    "test the example of Java version"{// 1s 308ms
        val executor = Executors.newSingleThreadScheduledExecutor()
        val atomic = AtomicInteger()
        val task = Runnable {
            print(".${atomic.getAndIncrement()}")
        }
        repeat(100_000) {
            executor.schedule(task, 1L, TimeUnit.SECONDS)
        }
        executor.shutdown()
        executor.awaitTermination(10L, TimeUnit.SECONDS)
        println("=============")
        println("--${atomic.get()}--")
    }
})