package com.zarinraim.garage.di

import com.zarinraim.garage.data.FetchedGarageRepositoryImpl
import com.zarinraim.garage.data.GarageRepositoryImpl
import com.zarinraim.garage.data.SelectedVinRepositoryImpl
import com.zarinraim.garage.domain.FetchedGarageRepository
import com.zarinraim.garage.domain.GarageRepository
import com.zarinraim.garage.domain.GarageUseCase
import com.zarinraim.garage.domain.SelectedVinRepository
import com.zarinraim.garage.network.di.NetworkGraph
import com.zarinraim.garage.scene.DetailViewModel
import com.zarinraim.garage.scene.OverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.new
import org.koin.dsl.module

object AppGraph {

    val module = module {
        includes(NetworkGraph.module)

        single<GarageRepository> { new(::GarageRepositoryImpl) }
        single<FetchedGarageRepository> { new(::FetchedGarageRepositoryImpl) }
        single<SelectedVinRepository> { new(::SelectedVinRepositoryImpl) }

        factoryOf(GarageUseCase::FetchOverview)
        factoryOf(GarageUseCase::LoadDetail)

        viewModelOf(::OverviewViewModel)
        viewModelOf(::DetailViewModel)
    }
}