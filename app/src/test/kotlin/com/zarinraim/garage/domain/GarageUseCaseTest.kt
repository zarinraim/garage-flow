package com.zarinraim.garage.domain

import com.zarinraim.garage.data.FetchedGarageRepositoryImpl
import com.zarinraim.garage.data.SelectedVinRepositoryImpl
import com.zarinraim.garage.model.Auto
import com.zarinraim.garage.model.Render
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GarageUseCaseTest {

    @Test
    fun `should store autos on success`() = runTest {
        val garage = listOf(
            auto(
                vin = "1234",
                name = "auto1",
                renders = listOf(Render.Detail("detail1"), Render.Overview("overview1"))
            ),
            auto(
                vin = "5678",
                name = "auto2",
                renders = listOf(Render.Overview("overview2"), Render.Detail("detail2"))
            )
        )
        val garageRepository = garageRepository(fetchResult = Result.success(garage))
        val fetchedGarageRepository = fetchedGarageRepository()
        val fetchOverview = fetchOverviewUseCase(
            repository = garageRepository,
            fetchedGarageRepository = fetchedGarageRepository
        )

        fetchOverview()

        fetchedGarageRepository.load() shouldBe garage
    }

    @Test
    fun `should load selected auto by vin`() = runTest {
        val garage = listOf(
            auto(
                vin = "1234",
                name = "auto1",
                renders = listOf(Render.Detail("detail1"), Render.Overview("overview1"))
            ),
            auto(
                vin = "5678",
                name = "auto2",
                renders = listOf(Render.Overview("overview2"), Render.Detail("detail2"))
            )
        )
        val selectedVinRepository = selectedVinRepository()
        val fetchedGarageRepository = fetchedGarageRepository()
        val fetchOverview = fetchOverviewUseCase(
            repository = garageRepository(fetchResult = Result.success(garage)),
            fetchedGarageRepository = fetchedGarageRepository,
        )
        val loadDetail = loadDetailUseCase(
            selectedVinRepository = selectedVinRepository,
            fetchedGarageRepository = fetchedGarageRepository
        )

        fetchOverview()
        selectedVinRepository.store("5678")

        loadDetail()
            .shouldBe(
                auto(
                    vin = "5678",
                    name = "auto2",
                    renders = listOf(Render.Overview("overview2"), Render.Detail("detail2"))
                )
            )
    }

    private fun auto(
        vin: String = "",
        name: String = "",
        model: String = "",
        trimLevel: String = "",
        engine: Auto.Engine = engine(),
        renders: List<Render> = emptyList()
    ) = Auto(
        vin = vin,
        name = name,
        model = model,
        trimLevel = trimLevel,
        engine = engine,
        renders = renders
    )

    private fun engine(
        type: String = "",
        powerInKw: Int = 110,
        capacityInLiters: Float = 1.1f
    ) = Auto.Engine(
        type = type,
        powerInKw = powerInKw,
        capacityInLiters = capacityInLiters
    )

    private fun garageRepository(
        fetchResult: Result<List<Auto>> = Result.success(emptyList())
    ) = object : GarageRepository {
        override suspend fun fetchGarage() = fetchResult
    }

    private fun fetchedGarageRepository(
        autos: List<Auto> = emptyList()
    ) = FetchedGarageRepositoryImpl().apply { store(autos) }

    private fun selectedVinRepository() = SelectedVinRepositoryImpl()

    private fun fetchOverviewUseCase(
        repository: GarageRepository = garageRepository(),
        fetchedGarageRepository: FetchedGarageRepository = fetchedGarageRepository()
    ) = GarageUseCase.FetchOverview(
        repository = repository,
        fetchedGarageRepository = fetchedGarageRepository
    )

    private fun loadDetailUseCase(
        selectedVinRepository: SelectedVinRepository = selectedVinRepository(),
        fetchedGarageRepository: FetchedGarageRepository = fetchedGarageRepository()
    ) = GarageUseCase.LoadDetail(
        selectedVinRepository = selectedVinRepository,
        fetchedGarageRepository = fetchedGarageRepository
    )
}