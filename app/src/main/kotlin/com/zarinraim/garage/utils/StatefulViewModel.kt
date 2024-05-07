package com.zarinraim.garage.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class StatefulViewModel<S : ViewModelState>(initialState: S) : ViewModel() {
    private val statesFlow = MutableStateFlow(initialState)

    protected var state: S
        get() = statesFlow.value
        set(value) {
            statesFlow.value = value
        }

    val states: StateFlow<S> = statesFlow
}

interface ViewModelState