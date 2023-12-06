package com.example.vicetracker.ViewViceActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.vicetracker.Model.DayAmount
import androidx.lifecycle.viewModelScope
import com.example.vicetracker.Model.Vice
import com.example.vicetracker.Model.ViceRepository
import com.example.vicetracker.NewViceActivity.NewViceViewModel
import kotlinx.coroutines.launch

class ViewViceViewModel(private val repository: ViceRepository, private val id:Int) : ViewModel() {
    var curVice: LiveData<Vice> = repository.getVice(id).asLiveData()
    var curViceDayAmounts: LiveData<List<DayAmount>> = repository.getViceDayAmounts(id).asLiveData()

    fun updateId(id:Int){
        curVice = repository.getVice(id).asLiveData()
        curViceDayAmounts = repository.getViceDayAmounts(id).asLiveData()
    }

    fun updateChecked(itemId: Int) {
        viewModelScope.launch {

        }
    }
}

class ViewViceViewModelFactory(private val repository: ViceRepository,private val id:Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewViceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewViceViewModel(repository,id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}