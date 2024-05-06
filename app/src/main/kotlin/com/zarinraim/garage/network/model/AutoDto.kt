package com.zarinraim.garage.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AutoDto(
    val vin: String,
    val name: String,
    val model: String,
    val trimLevel: String,
    val engine: EngineDto,
    val renders: List<RenderDto>
)


@Serializable
data class EngineDto(
    val type: String,
    val powerInKW: Int,
    val capacityInLiters: Float?,
)

@Serializable
data class RenderDto(
    val url: String,
    val viewPoint: String
)