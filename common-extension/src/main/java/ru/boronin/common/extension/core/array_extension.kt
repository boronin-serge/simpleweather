package ru.boronin.common.extension.core

import ru.boronin.common.extension.primitive.isNotOutToBounds

fun <T> Array<T>.isNotOutToBounds(index: Int) = size.isNotOutToBounds(index)
fun CharArray.isNotOutToBounds(index: Int) = size.isNotOutToBounds(index)