package com.example.marsphotos

import android.app.Application
import com.example.marsphotos.data.AppContainer
import com.example.marsphotos.data.DefaultAppContainer


// Inherits from the Application Object
class MarsPhotosApplication: Application() {
    // Declaring a Container of type AppContainer to store the DefaultAppContainer Object
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}







