package com.ayprotech.spacex.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayprotech.spacex.data.network.responses.launche.LaunchesItem

@Dao
interface LaunchItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(launchItem: List<LaunchesItem>)

    @Query("SELECT * FROM LaunchesItem")
    fun getLaunches(): LiveData<List<LaunchesItem>>

    @Query("DELETE  FROM LaunchesItem")
    suspend fun deleteAll()

    @Query("delete from LaunchesItem where date_unix in (:date_unix)")
    suspend fun deleteList(date_unix: List<Int>)

}