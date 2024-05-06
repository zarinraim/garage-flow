package com.zarinraim.garage.domain

import com.zarinraim.garage.model.Auto

interface GarageRepository {

    suspend fun fetchGarage(): Result<List<Auto>>
}