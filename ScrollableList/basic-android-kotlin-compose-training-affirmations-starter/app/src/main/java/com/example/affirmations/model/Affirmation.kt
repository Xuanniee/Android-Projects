package com.example.affirmations.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Affirmation(
    // Each Affirmation Post consists of 1 Image and 1 String
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)
