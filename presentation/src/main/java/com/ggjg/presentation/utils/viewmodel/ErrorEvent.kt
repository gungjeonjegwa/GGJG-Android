package com.ggjg.presentation.utils.viewmodel

sealed class ErrorEvent {
    object MoreDataError : ErrorEvent()
    object TokenError : ErrorEvent()
    object NotFoundError : ErrorEvent()
    object ConflictError : ErrorEvent()
    object UnknownError : ErrorEvent()
}