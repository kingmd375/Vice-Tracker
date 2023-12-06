package com.example.vicetracker.AllVicesActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.vicetracker.Model.Vice
import com.example.vicetracker.Model.ViceRepository
import kotlinx.coroutines.coroutineScope

class ViceListViewModel(private val repository: ViceRepository) : ViewModel() {
    // Using LiveData and caching what allVices returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allVices: LiveData<List<Vice>> = repository.allVices.asLiveData()

    suspend fun incrementViceAmount(vice: Vice) {
        coroutineScope {
            repository.incrementViceAmount(vice)
        }
    }
}

class ViceListViewModelFactory(private val repository: ViceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViceListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViceListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}