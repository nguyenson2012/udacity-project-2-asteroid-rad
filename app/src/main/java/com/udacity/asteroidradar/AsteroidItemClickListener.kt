package com.udacity.asteroidradar

class AsteroidItemClickListener(val clickListener: (Asteroid) -> Unit) {
    fun onClick(data: Asteroid) = clickListener(data)
}