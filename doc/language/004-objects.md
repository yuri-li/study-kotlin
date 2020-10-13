# 1 匿名内部类


# 2 虚拟代理
加载一张图片，假设，首次加载时，延迟1分钟
- 在延迟加载的过程中，显示一句话：图片正在加载

```
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
```