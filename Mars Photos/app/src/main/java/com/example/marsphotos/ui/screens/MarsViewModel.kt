/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens

import android.text.Spannable.Factory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.data.MarsPhotoRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Simply saving the UiState as a Mutable State prevents us from saving the different status
// like Loading, Error, and Success
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    // The 2 States below need not set new data and create new objects, which is why an object is sufficient for the web response
    object Error: MarsUiState
    object Loading: MarsUiState
    // Sealed Interface used instead of Interface to remove Else Branch
}

class MarsViewModel(private val marsPhotosRepository: MarsPhotoRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)     // Loading as Default Value
        // Setter is private to protect writes to the marsUiState
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API
     */
    private fun getMarsPhotos() {
        // Launch the Coroutine using a ViewModelScope
        viewModelScope.launch {
            // Might have Connectivity Issues
            marsUiState = try {
                // Within this Scope, use the Repository, not the Object to access the Data, abstracting the data within the Data Layer
                val listResult = marsPhotosRepository.getMarsPhotos()
                // Assign results from backend server to marsUiState {A mutable state object that represents the status of the most recent web request}
                MarsUiState.Success("Success: ${listResult.size} Mars Photos retrieved!!")
            }
            catch (e: IOException) {
                MarsUiState.Error
            }
            catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }

    //
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotoRepository = application.container.marsPhotoRepository
                MarsViewModel(marsPhotosRepository = marsPhotoRepository)
            }
        }

    }
}
