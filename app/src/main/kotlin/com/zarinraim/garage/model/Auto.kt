package com.zarinraim.garage.model

data class Auto(
    val vin: String,
    val name: String,
    val model: String,
    val trimLevel: String,
    val engine: Engine,
    val renders: List<Render>
) {
    data class Engine(
        val type: String,
        val powerInKw: Int,
        val capacityInLiters: Float?,
    )
}