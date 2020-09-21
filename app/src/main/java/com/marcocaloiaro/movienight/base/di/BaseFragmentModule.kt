package com.marcocaloiaro.movienight.base.di

import android.app.SearchManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(FragmentComponent::class)
object BaseFragmentModule {

    @Provides
    fun provideSearchManager(@ApplicationContext applicationContext: Context) : SearchManager {
        return applicationContext.getSystemService(Context.SEARCH_SERVICE) as SearchManager
    }

}