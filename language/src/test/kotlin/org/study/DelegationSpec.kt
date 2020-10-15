package org.study

import io.kotest.core.spec.style.StringSpec
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class DelegationSpec : StringSpec({
    "test delegation"{
        val chrome = ChromeBrowser(ImageLoader())
        chrome.loadImage()
        Thread.sleep(10000)
    }
}) {
    suspend fun greet(){
        println("hello world")
    }

    interface LoadImage {
        fun loading():Unit
    }

    class TextLoader : LoadImage {
        override fun loading() = println("图片正在加载...")
    }

    class ImageLoader : LoadImage {
        override fun loading() = println("<div>图片正在加载...</div>")
    }

    class ChromeBrowser(loader: LoadImage) : LoadImage by loader{
        fun loadImage() {
            thread {
                sleep(5000)
                println("图片加载完毕")
            }
            this.loading()
        }
    }

}