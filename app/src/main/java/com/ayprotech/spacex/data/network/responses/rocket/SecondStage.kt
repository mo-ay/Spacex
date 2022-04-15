package com.ayprotech.spacex.data.network.responses.rocket

data class SecondStage(
    val burn_time_sec: Int,
    val engines: Int,
    val fuel_amount_tons: Double,
    val payloads: Payloads,
    val reusable: Boolean,
    val thrust: Thrust
)