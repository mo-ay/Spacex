package com.ayprotech.spacex.data.network.responses.launche

data class Core(
    val core: String,
    val flight: Any,
    val gridfins: Any,
    val landing_attempt: Any,
    val landing_success: Any,
    val landing_type: Any,
    val landpad: Any,
    val legs: Any,
    val reused: Boolean
)