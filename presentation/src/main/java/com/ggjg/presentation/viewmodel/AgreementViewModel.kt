package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgreementViewModel @Inject constructor(
): ViewModel() {
    companion object {
        var title = ""
        var content = ""
    }
}