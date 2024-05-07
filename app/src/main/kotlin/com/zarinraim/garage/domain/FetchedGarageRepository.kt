package com.zarinraim.garage.domain

import com.zarinraim.garage.model.Auto

interface FetchedGarageRepository {
    fun store(autos: List<Auto>)
    fun load(): List<Auto>
    fun loadOne(vin: String): Auto
}