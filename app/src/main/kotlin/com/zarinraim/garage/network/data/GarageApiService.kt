package com.zarinraim.garage.network.data

import com.zarinraim.garage.network.model.AutoDto
import retrofit2.http.GET

interface GarageApiService {

    @GET("garage_api.json")
    suspend fun getGarageData(): List<AutoDto>
}