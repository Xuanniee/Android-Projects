package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


/**
 * A container is an object that contains the dependencies that the app requires.
 * These dependencies are used across the whole application, so they need to be in a common place
 * that all activities can use. You can create a subclass of the Application class and store a
 * reference to the container.
 */
interface AppContainer {
    // Abstract Property of type MarsPhotoRepository
    val marsPhotoRepository: MarsPhotoRepository
}

/**
 * Class that implements the Interface App Container. Container that maintains the Dependencies
 */
class DefaultAppContainer() : AppContainer {
    // Retrofit requires a Base URI for the Web Service and a Converter Factory to build a web services API
    // Not Const anymore as it is not a Top Level Variable but a Property of this Class
    private val BASE_URL =
        "https://android-kotlin-fun-mars-server.appspot.com"

    // Retrofit Builder to build and create a Retrofit Object
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))  // To convert JSON data into primitive types like Strings
        .baseUrl(BASE_URL)
        .build()

    // Lazily initialized retrofit object property to ensure it is initialised at first usage
    // Private since this variable is only used within the class by the marsPhotoRepo property, and need not be accessible outside
    private val retrofitService: MarsApiService by lazy {
        // Better to call webservice from a repository in future work and not a singleton object
        retrofit.create(MarsApiService::class.java)
    }

    // Since we implement the AppContainer interface, we need to override the marsPhotoRepo property above
    override val marsPhotoRepository: MarsPhotoRepository by lazy {
        DefaultMarsPhotoRepository(retrofitService)
    }
}













