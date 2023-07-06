package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidAPIService
import com.udacity.asteroidradar.api.asListAsteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidsRepository(private val asteroidDB: AsteroidDB) {
    val asteroids: LiveData<List<Asteroid>> =
        asteroidDB.asteroidDao.getAll().map { it.toListAsteroid() }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids = AsteroidAPIService.getAsteroids()
            asteroidDB.asteroidDao.insertAll(asteroids.asListAsteroid())
        }
    }

    suspend fun getPictureOfDay(): PictureOfDay {
        lateinit var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = AsteroidAPIService.getPictureOfDay()
        }
        return pictureOfDay
    }
}