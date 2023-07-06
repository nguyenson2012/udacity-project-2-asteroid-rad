package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDB
import com.udacity.asteroidradar.database.AsteroidsRepository
import retrofit2.HttpException

class AsteroidWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "AsteroidWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidDB.getInstance(applicationContext)
        val repository = AsteroidsRepository(database)
        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}