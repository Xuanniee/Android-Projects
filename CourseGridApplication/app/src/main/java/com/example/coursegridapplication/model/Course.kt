package com.example.coursegridapplication.model

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val courseTitleResourceId: Int,
    val numberAvailableCourses: Int,
    @DrawableRes val courseImageResourceId: Int
)