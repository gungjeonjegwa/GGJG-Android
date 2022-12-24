package com.example.ggjg_andorid.utils

import android.util.Patterns
import java.util.regex.Pattern

fun String.isPhoneNumber(): Boolean =
    !Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", this)

fun String.isEmail(): Boolean =
    !Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isPassword(): Boolean =
    !Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,30}$", this)
