package com.example.marsphotos.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Data Class required to store the parsed data from the JSON
@Serializable
data class MarsPhoto(
    // Photo API JSON Object
    val id: String,
    // When Key Names are not suitable / compatible
    @SerialName(value = "img_src")
    val imgSrc: String
)