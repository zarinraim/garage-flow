package com.zarinraim.garage.data

import com.zarinraim.garage.domain.GarageRepository
import com.zarinraim.garage.model.Auto
import com.zarinraim.garage.model.Render
import com.zarinraim.garage.network.data.GarageApiService
import com.zarinraim.garage.network.model.AutoDto
import com.zarinraim.garage.network.model.EngineDto
import com.zarinraim.garage.network.model.RenderDto

class GarageRepositoryImpl(
    private val api: GarageApiService,
) : GarageRepository {

    override suspend fun fetchGarage(): Result<List<Auto>> = runCatching {
        api.getGarageData().map(::toDomain)
    }

    private fun toDomain(external: AutoDto) = with(external) {
        Auto(
            vin = vin,
            name = name,
            model = model,
            trimLevel = trimLevel,
            engine = engine.toDomain(),
            renders = renders.mapNotNull { it.toDomain() },
        )
    }

    private fun EngineDto.toDomain() = Auto.Engine(
        type = type,
        powerInKw = powerInKW,
        capacityInLiters = capacityInLiters
    )

    private fun RenderDto.toDomain() = when (viewPoint) {
        "garage_l" -> Render.Overview(url = url)
        "main" -> Render.Detail(url = url)
        else -> null
    }
}