package com.zarinraim.garage.domain

import com.zarinraim.garage.model.Auto
import com.zarinraim.garage.utils.SuspendUnitUseCase
import com.zarinraim.garage.utils.UnitUseCase

interface GarageUseCase {

    class FetchOverview(
        private val repository: GarageRepository,
        private val fetchedGarageRepository: FetchedGarageRepository,
    ) : SuspendUnitUseCase<Result<List<Auto>>> {
        // TODO make errors user friendly aka handle common errors
        override suspend fun invoke(): Result<List<Auto>> {
            return repository.fetchGarage()
                .onSuccess { fetchedGarageRepository.store(it) }
        }
    }

    class LoadDetail(
        private val selectedVinRepository: SelectedVinRepository,
        private val fetchedGarageRepository: FetchedGarageRepository,
    ) : UnitUseCase<Auto> {
        override fun invoke(): Auto {
            val vin = selectedVinRepository.load()
            return fetchedGarageRepository.loadOne(vin)
        }
    }
}