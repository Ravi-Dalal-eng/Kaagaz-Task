package com.messaging.kaagaztask.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OurViewModelFactory(private val ourRepository: OurRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OurViewModel(ourRepository) as T
    }
}