package com.marcocaloiaro.movienight.base.di

import android.content.Context
import androidx.room.Room
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.base.local.AppDatabase
import com.marcocaloiaro.movienight.base.local.ShowsDao
import com.marcocaloiaro.movienight.base.network.ShowsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShowsApiService(@ApplicationContext appContext: Context) : ShowsApiService {
        return Retrofit.Builder()
            .baseUrl(appContext.getString(R.string.retrofit_service_base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShowsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "shows-db").build()
    }

    @Provides
    @Singleton
    fun provideDatabaseDao(roomDatabase: AppDatabase) : ShowsDao {
        return roomDatabase.showsDao()
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IoDispatcher

    @Provides
    @IoDispatcher
    fun provideIODispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }

}