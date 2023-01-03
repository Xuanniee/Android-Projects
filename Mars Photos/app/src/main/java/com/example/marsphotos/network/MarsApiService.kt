package com.example.marsphotos.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.GET
import okhttp3.MediaType.Companion.toMediaType

//// Retrofit requires a Base URI for the Web Service and a Converter Factory to build a web services API
//// Constant for the Base URI
//private const val BASE_URL =
//    "https://android-kotlin-fun-mars-server.appspot.com"
//
//// Retrofit Builder to build and create a Retrofit Object
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))  // To convert JSON data into primitive types like Strings
//    .baseUrl(BASE_URL)
//    .build()

// Define an Interface that details how Retrofit communicates with the Web Service using the HTTP Protocol
interface MarsApiService{
    /**
     *  Function to get Images Response String from URI by specifying Type of Request and Endpoint like "/photos" a URL of sorts
     */
    // Returning a List of MarsPhoto Object from the Json array instead of a string
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
        // When invoked, /photos is appended to Base URL


}



