package com.zarinraim.garage.di

import com.zarinraim.garage.data.GarageRepositoryImpl
import com.zarinraim.garage.domain.GarageRepository
import com.zarinraim.garage.network.di.NetworkGraph
import org.koin.core.module.dsl.new
import org.koin.dsl.module

object AppGraph {

    val module = module {
        includes(NetworkGraph.module)

        single<GarageRepository> { new(::GarageRepositoryImpl) }
    }
}