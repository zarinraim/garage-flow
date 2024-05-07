package com.zarinraim.garage.data

import com.zarinraim.garage.domain.SelectedVinRepository

class SelectedVinRepositoryImpl : SelectedVinRepository {

    private var value: String? = null

    override fun store(vin: String) {
        value = vin
    }

    override fun load(): String = checkNotNull(value)
}