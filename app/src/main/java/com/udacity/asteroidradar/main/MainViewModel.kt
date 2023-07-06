package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDB
import com.udacity.asteroidradar.database.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val asteroidDB = AsteroidDB.getInstance(app)
    private val asteroidRepo = AsteroidsRepository(asteroidDB)
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay
    private val _clickedAsteroid = MutableLiveData<Asteroid?>()
    val clickedAsteroid: LiveData<Asteroid?>
        get() = _clickedAsteroid

    val asteroids = asteroidRepo.asteroids

    init {
        refreshData()
        getPictureOfDay()
    }

    private fun refreshData() {
        viewModelScope.launch {
            try {
                asteroidRepo.refreshAsteroids()
            } catch (e: Exception) {
                Log.e("MainViewModel", e.toString())
            }
        }
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = asteroidRepo.getPictureOfDay()
            } catch (e: Exception) {
                Log.e("MainViewModel", e.toString())
            }
        }
    }

    fun onAsteroidItemClick(asteroid: Asteroid) {
        _clickedAsteroid.value = asteroid
    }

    fun removeClickedItem() {
        _clickedAsteroid.value = null
    }
}