package com.example.xhike

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform