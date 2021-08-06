package ru.nukolay.stupnikov.animelist.util

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.BiFunction
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

fun <T, U> Single<T>.zipWith(other: SingleSource<U>): Single<Pair<T,U>>
        = zipWith(other, BiFunction { t, u -> Pair(t,u) })