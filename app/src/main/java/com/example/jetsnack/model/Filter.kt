package com.example.jetsnack.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.jetsnack.R

@Stable
class Filter(val name: String, enabled: Boolean = false, @DrawableRes val icon: Int? = null) {
    val enabled = mutableStateOf(enabled)
}

val filters = listOf(
    Filter(name = "Organic"),
    Filter(name = "Gluten-free"),
    Filter(name = "Dairy-free"),
    Filter(name = "Sweet"),
    Filter(name = "Savory"),
)
val priceFilters = listOf(
    Filter(name = "$"),
    Filter(name = "$$"),
    Filter(name = "$$$"),
    Filter(name = "$$$$"),
)
val sortFilters = listOf(
    Filter(name = "Android's favorite (default)", icon = R.drawable.ic_android),
    Filter(name = "Rating", icon = R.drawable.ic_star),
    Filter(name = "Alphabetical", icon = R.drawable.ic_sort_by_alpha),
)

val categoryFilters = listOf(
    Filter(name = "Chips & crackers"),
    Filter(name = "Fruit snacks"),
    Filter(name = "Desserts"),
    Filter(name = "Nuts"),
)
val lifeStyleFilters = listOf(
    Filter(name = "Organic"),
    Filter(name = "Gluten-free"),
    Filter(name = "Dairy-free"),
    Filter(name = "Sweet"),
    Filter(name = "Savory"),
)

var sortDefault = sortFilters[0].name