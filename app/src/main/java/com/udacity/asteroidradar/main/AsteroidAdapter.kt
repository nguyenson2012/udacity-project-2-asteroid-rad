package com.udacity.asteroidradar.main

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidItemClickListener

class AsteroidAdapter(private val itemClickListener: AsteroidItemClickListener)
    : ListAdapter<Asteroid, AsteroidViewHolder>(AsteroidDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener)
    }
}