package com.zarinraim.garage.scene

import com.zarinraim.garage.domain.GarageUseCase
import com.zarinraim.garage.model.Auto
import com.zarinraim.garage.presentation.AutoDetailFormat
import com.zarinraim.garage.presentation.AutoDetailState
import com.zarinraim.garage.scene.DetailViewModel.State
import com.zarinraim.garage.utils.StatefulViewModel
import com.zarinraim.garage.utils.ViewModelState

class DetailViewModel(
    loadDetail: GarageUseCase.LoadDetail,
) : StatefulViewModel<State>(defaultState(loadDetail())) {

    private companion object {
        fun defaultState(auto: Auto): State = State(detail = AutoDetailFormat.format(auto))
    }

    data class State(
        val detail: AutoDetailState
    ) : ViewModelState
}