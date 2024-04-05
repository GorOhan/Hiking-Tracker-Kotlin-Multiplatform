package com.ohanyan.xhike

import org.koin.core.component.KoinComponent


class Greeting: KoinComponent {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}