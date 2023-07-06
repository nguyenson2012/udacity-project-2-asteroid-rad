package com.udacity.asteroidradar

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.udacity.asteroidradar.worker.AsteroidWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication: Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    private val isDebug = false

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() = applicationScope.launch {
        configWorker()
    }

    private fun configWorker() {
        var constraints: Constraints = if (isDebug) {
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        } else {
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .apply {
                    setRequiresDeviceIdle(true)
                }.build()
        }

        val timeUnit = if(isDebug) TimeUnit.SECONDS else TimeUnit.DAYS
        val interval:Long = if(isDebug) 10 else 1
        val repeatingRequest = PeriodicWorkRequestBuilder<AsteroidWorker>(interval, timeUnit)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            AsteroidWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            repeatingRequest)
    }
}