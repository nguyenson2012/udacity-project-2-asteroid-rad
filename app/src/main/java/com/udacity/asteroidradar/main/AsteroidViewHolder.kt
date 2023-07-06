package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidItemClickListener
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidViewHolder private constructor(private val itemAsteroidBinding: ItemAsteroidBinding)
    : RecyclerView.ViewHolder(itemAsteroidBinding.root) {
    companion object {
        fun create(parent: ViewGroup) : AsteroidViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemAsteroidBinding.inflate(layoutInflater, parent, false)
            return AsteroidViewHolder(binding)
        }
    }

    fun bind(item: Asteroid, clickListener: AsteroidItemClickListener) {
        itemAsteroidBinding.asteroid = item
        itemAsteroidBinding.clickListener = clickListener
    }
}