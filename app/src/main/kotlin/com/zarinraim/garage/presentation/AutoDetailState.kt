package com.zarinraim.garage.presentation

data class AutoDetailState(
    val vin: String,
    val name: String,
    val model: String,
    val trimLevel: String,
    val engine: String,
    val power: String,
    val imageUrl: String?
)