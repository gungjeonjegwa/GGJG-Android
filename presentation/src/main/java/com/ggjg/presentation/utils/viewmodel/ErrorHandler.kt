package com.ggjg.presentation.utils.viewmodel

import android.util.Log
import com.ggjg.domain.exception.*

suspend fun Throwable.errorHandling(
    moreDataAction: suspend () -> Unit = {},
    tokenErrorAction: suspend () -> Unit = {},
    notFoundAction: suspend () -> Unit = {},
    conflictAction: suspend () -> Unit = {},
    unknownAction: suspend () -> Unit = {},
): ErrorEvent =
    when (this) {
        is MoreDataException -> {
            errorLog("MoreDataException", message)
            moreDataAction()
            ErrorEvent.MoreDataError
        }
        is TokenErrorException, is NeedLoginException -> {
            errorLog("TokenErrorException", message)
            tokenErrorAction()
            ErrorEvent.TokenError
        }
        is NotFoundException -> {
            errorLog("NotFoundException", message)
            notFoundAction()
            ErrorEvent.NotFoundError
        }
        is ConflictException -> {
            errorLog("ConflictException", message)
            conflictAction()
            ErrorEvent.ConflictError
        }
        else -> {
            errorLog("UnKnownException", message)
            unknownAction()
            ErrorEvent.UnknownError
        }
    }

private fun errorLog(tag: String, msg: String?) {
    Log.d("ErrorHandling-$tag", msg ?: "알 수 없는 오류")
}