package com.zarinraim.garage.presentation

import com.zarinraim.garage.model.Auto
import com.zarinraim.garage.model.Render

object AutoOverviewFormat {

    fun format(auto: Auto) = AutoOverviewState(
        vin = auto.vin,
        name = auto.name,
        imageUrl = auto.renders.filterIsInstance<Render.Overview>().firstOrNull()?.url
    )
}