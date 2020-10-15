package org.study.structual

import io.kotest.core.spec.style.StringSpec

class AdapterSpec : StringSpec({
    "test extension function"{
        fun String.openUrl() {
            //Get the name of the operating system
            val osName = System.getProperty("os.name")
            when {
                osName.startsWith("Mac OS") -> Class.forName("com.apple.eio.FileManager").getDeclaredMethod("openURL", *arrayOf<Class<*>>(String::class.java)).invoke(null, *arrayOf<Any>(this))
                osName.startsWith("Windows") -> Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler ${this}")
                else -> {
                    var flag = true
                    // Unix or Linux
                    arrayOf("firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape").forEach {
                        if (Runtime.getRuntime().exec(arrayOf("which", it)).waitFor() == 0) {
                            flag = false
                            Runtime.getRuntime().exec(arrayOf<String>(it, this))
                            return
                        }
                    }
                    if (flag) throw Exception("Could not find web browser")
                }
            }
        }
        fun Array<String>.openUrl() = this.forEach {
            it.openUrl()
        }
        arrayOf("https://www.google.com", "https://www.baidu.com").openUrl()
    }
})