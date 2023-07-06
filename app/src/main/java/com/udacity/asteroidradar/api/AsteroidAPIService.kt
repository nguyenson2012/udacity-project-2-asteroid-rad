package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object AsteroidAPIService {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.BASE_URL)
        .build()
    private val asterodiAPiService : AsteroidIAPIInterface by lazy {
        retrofitBuilder.create(AsteroidIAPIInterface::class.java)
    }

    suspend fun getAsteroids() : List<Asteroid> {
        val responseStr = asterodiAPiService.getAsteroids("","", Constants.API_KEY)
        val responseJsonObject = JSONObject(responseStr)
        return parseAsteroidsJsonResult(responseJsonObject)
    }

    suspend fun getPictureOfDay() = asterodiAPiService.getPictureOfDay(Constants.API_KEY)
}