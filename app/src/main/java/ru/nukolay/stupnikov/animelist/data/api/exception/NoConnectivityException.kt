package ru.nukolay.stupnikov.animelist.data.api.exception

import java.io.IOException

class NoConnectivityException : IOException() {

    override fun getLocalizedMessage(): String {
        return "No Internet Connection"
    }
}