package ru.nukolay.stupnikov.animelist

object StaticConfig {

    val TIMEOUT_CONNECTION = if (BuildConfig.DEBUG) 300 else 45.toLong()
    val TIMEOUT_SOCKET = if (BuildConfig.DEBUG) 300 else 30.toLong()

    const val PAGE_LIMIT = 20
}