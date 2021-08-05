package ru.nukolay.stupnikov.animelist.util

import androidx.lifecycle.MutableLiveData
import java.lang.StringBuilder

fun List<String>.toSingleString(): String {
    val builder = StringBuilder()
    for (value in this) {
        builder.append("$value,")
    }
    return builder.toString().substring(0, builder.toString().length - 1)
}

fun <T> MutableLiveData<MutableList<T>>.addAll(list: List<T>) {
    val value = this.value ?: mutableListOf()
    value.addAll(list)
    this.value = value
}

fun <T> MutableLiveData<MutableList<T>>.clear() {
    this.value = mutableListOf()
}