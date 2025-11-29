package eu.tutorials.l6_ex

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Country(
    @StringRes val name: Int,
    @DrawableRes val flagRes: Int
)
val countries = listOf(
    Country(R.string.country_finland, R.drawable.flag_finland),
    Country(R.string.country_japan, R.drawable.flag_japan),
    Country(R.string.country_brazil, R.drawable.flag_brazil),
    Country(R.string.country_canada, R.drawable.flag_canada)
)

