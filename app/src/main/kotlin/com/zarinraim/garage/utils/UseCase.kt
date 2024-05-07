package com.zarinraim.garage.utils

interface SuspendUnitUseCase<out Output> {

    suspend operator fun invoke(): Output
}

interface UnitUseCase<out Output> {

    operator fun invoke(): Output
}