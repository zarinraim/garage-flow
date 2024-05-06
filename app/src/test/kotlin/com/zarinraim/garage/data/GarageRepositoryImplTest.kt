package com.zarinraim.garage.data

import com.zarinraim.garage.model.Auto
import com.zarinraim.garage.model.Render
import com.zarinraim.garage.network.data.GarageApiService
import com.zarinraim.garage.network.model.AutoDto
import com.zarinraim.garage.network.model.EngineDto
import com.zarinraim.garage.network.model.RenderDto
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GarageRepositoryImplTest {

    @Test
    fun `should convert renders to domain`() = runTest {
        val dto = listOf(
            autoDto(
                renders = listOf(
                    RenderDto(url = "url overview", viewPoint = "garage_l"),
                    RenderDto(url = "url detail", viewPoint = "main"),
                )
            )
        )

        val api = api(result = dto)
        val repository = repository(api = api)

        val result: Result<List<Auto>> = repository.fetchGarage()

        result
            .shouldBeSuccess()
            .single()
            .renders shouldBe listOf(Render.Overview("url overview"), Render.Detail("url detail"))
    }

    private fun autoDto(
        vin: String = "",
        name: String = "",
        model: String = "",
        trimLevel: String = "",
        engine: EngineDto = engineDto(),
        renders: List<RenderDto> = emptyList()
    ) = AutoDto(
        vin = vin,
        name = name,
        model = model,
        trimLevel = trimLevel,
        engine = engine,
        renders = renders
    )

    private fun engineDto(
        type: String = "",
        powerInKW: Int = 0,
        capacityInLiters: Float = 0f
    ) = EngineDto(
        type = type,
        powerInKW = powerInKW,
        capacityInLiters = capacityInLiters
    )

    private fun api(result: List<AutoDto> = emptyList()): GarageApiService = mockk {
        coEvery { getGarageData() } returns result
    }

    private fun repository(api: GarageApiService = api()) = GarageRepositoryImpl(api = api)
}