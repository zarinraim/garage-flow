package com.zarinraim.garage.domain

interface SelectedVinRepository {
    fun store(vin: String)
    fun load(): String
}