package com.example.ggjg_andorid.utils.viewmodel

sealed class ErrorEvent {
    object MoreDataError : ErrorEvent()
    object TokenError : ErrorEvent()
    object NotFoundError : ErrorEvent()
    object ConflictError : ErrorEvent()
    object UnknownError : ErrorEvent()
}