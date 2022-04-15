package com.ayprotech.spacex.data.repositories

import com.ayprotech.spacex.data.network.MyApi
import javax.inject.Inject

class RocketRepository @Inject constructor(
    private val api: MyApi
) : BaseRepository() {

    suspend fun getRocketCall(rocketId: String) = safeApiCall { api.getRocket(rocketId) }

}