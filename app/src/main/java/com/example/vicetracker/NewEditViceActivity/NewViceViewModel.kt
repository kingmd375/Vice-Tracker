package com.example.vicetracker.NewEditViceActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.vicetracker.Model.Vice
import com.example.vicetracker.Model.ViceRepository
import kotlinx.coroutines.coroutineScope

class NewViceViewModel(private val repository: ViceRepository, private val id:Int) : ViewModel() {
    var curVice: LiveData<Vice> = repository.getVice(id).asLiveData()

    fun updateId(id:Int){
        curVice = repository.getVice(id).asLiveData()
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    suspend fun insert(vice: Vice){
        coroutineScope {
            repository.insert(vice)
        }
    }

    /**
     * Launching a new coroutine to Update the data in a non-blocking way
     */
    suspend fun update(vice: Vice) {
        coroutineScope {
            repository.update(vice)
        }
    }

    suspend fun deleteVice(id: Int) {
        coroutineScope {
            Log.d("ViewModel","Deleting id: $id")
            repository.deleteVice(id)
        }

    }
}

class NewViceViewModelFactory(private val repository: ViceRepository,private val id:Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewViceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewViceViewModel(repository,id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}