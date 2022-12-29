package com.example.data.utils

fun String.removeDot(): String {
    return this.replace("^\"|\"$".toRegex(), "")
}