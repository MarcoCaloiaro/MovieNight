package com.marcocaloiaro.movienight.base.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.base.adapters.MainFragmentPagerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(FragmentComponent::class)
object ViewPagerModule {

    @Provides
    fun provideMainFragmentAdapter(@ActivityContext activityContext: Context) : MainFragmentPagerAdapter {
        val fragment = (activityContext as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.container)
        fragment?.let {
            return MainFragmentPagerAdapter(fragment)
        }
        return MainFragmentPagerAdapter(Fragment())
    }

}