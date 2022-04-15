package com.ayprotech.spacex.di

import android.content.Context
import com.ayprotech.spacex.data.db.AppDatabase
import com.ayprotech.spacex.data.network.MyApi
import com.ayprotech.spacex.data.network.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMyApi(
    ): MyApi {
        return Network.retrofitService
    }

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.invoke(context)
    }

}