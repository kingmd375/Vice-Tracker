package com.example.vicetracker.ViewViceActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.vicetracker.Model.Vice
import com.example.vicetracker.Model.ViceRepository
import com.example.vicetracker.NewEditViceActivity.NewViceViewModel

class ViewViceViewModel(private val repository: ViceRepository, private val id:Int) : ViewModel() {
    var curVice: LiveData<Vice> = repository.getVice(id).asLiveData()
}

class ViewViceViewModelFactory(private val repository: ViceRepository,private val id:Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewViceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewViceViewModel(repository,id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}