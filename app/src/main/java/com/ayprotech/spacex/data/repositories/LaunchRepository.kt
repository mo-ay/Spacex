package com.ayprotech.spacex.data.repositories

import com.ayprotech.spacex.data.db.AppDatabase
import com.ayprotech.spacex.data.network.MyApi

import com.ayprotech.spacex.data.network.responses.launche.Launches
import javax.inject.Inject

class LaunchRepository @Inject constructor(
    private val api: MyApi,
    private val db: AppDatabase
) : BaseRepository() {

    suspend fun getLaunchesCall() = safeApiCall { api.getLaunches() }

    suspend fun saveLaunches(launches: Launches) = db.getLaunchesItemDAO().insert(launches)

    fun getLaunchesDb() = db.getLaunchesItemDAO().getLaunches()

    suspend fun deleteLaunchesDb() = db.getLaunchesItemDAO().deleteAll()

}