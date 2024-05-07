package com.zarinraim.garage.data

import com.zarinraim.garage.domain.FetchedGarageRepository
import com.zarinraim.garage.model.Auto
import kotlinx.coroutines.flow.MutableStateFlow

class FetchedGarageRepositoryImpl : FetchedGarageRepository {

    private val garage = MutableStateFlow<List<Auto>>(emptyList())

    override fun store(autos: List<Auto>) {
        garage.value = autos
    }

    override fun load(): List<Auto> = garage.value

    override fun loadOne(vin: String): Auto {
        return checkNotNull(garage.value.find { it.vin == vin })
    }
}