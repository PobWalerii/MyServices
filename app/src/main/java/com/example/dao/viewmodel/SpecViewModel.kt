package com.example.dao.viewmodel

import androidx.lifecycle.*
import com.example.dao.dataclass.KlientAndMark
import com.example.dao.dataclass.Specialist
import com.example.dao.repository.SpecRepository
import kotlinx.coroutines.launch

class SpecViewModel(val repository: SpecRepository): ViewModel() {

    private val _allSpec: MutableLiveData<List<Specialist>> = MutableLiveData()
    val allSpec: LiveData<List<Specialist>> = _allSpec

    fun insert(specialist: Specialist) = viewModelScope.launch {
        repository.insertAll(specialist)
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _allSpec.postValue(repository.getSpecialists())
        }
    }
}


class SpecViewModelFactory(val repository: SpecRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpecViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SpecViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}