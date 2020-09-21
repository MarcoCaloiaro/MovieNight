package com.marcocaloiaro.movienight.base.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcocaloiaro.movienight.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setDefaultFragment()
    }

    private fun setDefaultFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.container,
            MainFragment()
        ).commit()
    }
}