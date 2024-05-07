package com.zarinraim.garage.scene

import androidx.lifecycle.viewModelScope
import com.zarinraim.garage.domain.GarageUseCase
import com.zarinraim.garage.domain.SelectedVinRepository
import com.zarinraim.garage.model.Auto
import com.zarinraim.garage.presentation.AutoOverviewFormat
import com.zarinraim.garage.presentation.AutoOverviewState
import com.zarinraim.garage.scene.OverviewViewModel.State
import com.zarinraim.garage.utils.StatefulViewModel
import com.zarinraim.garage.utils.ViewModelState
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val fetchOverview: GarageUseCase.FetchOverview,
    private val selectedVinRepository: SelectedVinRepository,
) : StatefulViewModel<State>(State()) {

    init {
        fetch()
    }

    fun onCard(vin: String) {
        selectedVinRepository.store(vin)
    }

    private fun fetch() = viewModelScope.launch {
        fetchOverview().fold(
            onSuccess = { autos -> state = autos.asSuccess() },
            onFailure = { throwable -> state = throwable.localizedMessage.asFailure() }
        )
    }

    private fun List<Auto>.asSuccess() = State(
        items = map(AutoOverviewFormat::format),
        isLoading = false,
        error = null
    )

    private fun String?.asFailure() = State(
        items = emptyList(),
        isLoading = false,
        error = this ?: "Unknown error"
    )

    data class State(
        val items: List<AutoOverviewState> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
    ) : ViewModelState
}