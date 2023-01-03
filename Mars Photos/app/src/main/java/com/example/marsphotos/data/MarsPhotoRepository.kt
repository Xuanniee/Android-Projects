package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.example.marsphotos.network.MarsPhoto

// Naming is Type of Data + Repository
interface MarsPhotoRepository {
    // Returns a List of MarsPhoto Objects
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

// For implementing the above interface
class DefaultMarsPhotoRepository(
    // Adding a Constructor Parameter to allow AppContainer to use
    private val marsApiService: MarsApiService
): MarsPhotoRepository {
    // Override the Abstract Function to get Mars Photos
    override suspend fun getMarsPhotos(): List<MarsPhoto> {
        // Return the data from calling on the Object
        return marsApiService.getPhotos()
    }
}









