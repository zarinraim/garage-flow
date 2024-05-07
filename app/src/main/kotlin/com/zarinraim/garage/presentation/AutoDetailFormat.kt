package com.zarinraim.garage.presentation

import com.zarinraim.garage.model.Auto
import com.zarinraim.garage.model.Render

object AutoDetailFormat {

    fun format(auto: Auto) = with(auto) {
        AutoDetailState(
            vin = vin,
            name = name,
            model = model,
            trimLevel = trimLevel,
            engine = engine.toEngine(),
            power = engine.toPower(),
            imageUrl = renders.filterIsInstance<Render.Detail>().firstOrNull()?.url
        )
    }

    private fun Auto.Engine.toEngine() = buildString {
        capacityInLiters?.let { capacity ->
            append("$capacity L")
            append(" ")
        }
        append(type)
    }

    private fun Auto.Engine.toPower() = "$powerInKw kW"
}