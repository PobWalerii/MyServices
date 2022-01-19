package com.example.dao.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.dao.dataclass.Klient
import com.example.dao.dataclass.KlientAndMark
import com.example.dao.repository.KliRepository
import kotlinx.coroutines.launch

class KliViewModel(val repository: KliRepository) : ViewModel() {

    private val _allKli: MutableLiveData<List<KlientAndMark>> = MutableLiveData()
    val allKli: LiveData<List<KlientAndMark>> = _allKli
    
    fun insert(klient: Klient) = viewModelScope.launch {
        repository.insertAll(klient)
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _allKli.postValue(repository.getKlients())
        }
    }

}

class KliViewModelFactory(val repository: KliRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KliViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return KliViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}