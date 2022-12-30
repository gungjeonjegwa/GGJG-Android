package com.example.ggjg_andorid.utils

import android.util.Patterns
import java.io.File
import java.util.regex.Pattern

fun String.isPhoneNumber(): Boolean =
    !Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", this)

fun String.isEmail(): Boolean =
    !Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isPassword(): Boolean =
    !Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,30}$", this)

fun String.toDeliveryType(): String = when (this) {
    "WAITORDER" -> "배송준비중"
    "DELIVERYING" -> "배송중"
    "COMPLETEDELIVERY" -> "배송완료"
    "WAITCANCEL" -> "취소대기중"
    "CANCEL" -> "취소완료"
    "WAITRETURN" -> "환불대기중"
    "RETURN" -> "환불완료"
    else -> "배송준비중"
}

fun String.getDirectoryPaths(): List<String> {
    var directoryList = listOf<String>()
    File(this).listFiles().forEach {
        if (it.isDirectory) {
            directoryList = directoryList.plus(it.absolutePath)
        }
    }
    return directoryList
}

fun String.getFilePaths(): List<String> {
    var directoryList = listOf<String>()
    File(this).listFiles().forEach {
        if (it.isFile) {
            directoryList = directoryList.plus(it.absolutePath)
        }
    }
    return directoryList
}