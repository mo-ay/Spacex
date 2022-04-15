package com.ayprotech.spacex.data.network.responses.rocket

data class PayloadWeight(
    val id: String,
    val kg: Int,
    val lb: Int,
    val name: String
)