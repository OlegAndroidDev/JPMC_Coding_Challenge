package com.example.jpmc_coding_challenge.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jpmc_coding_challenge.data.api.ApiRepositoryImpl
import com.example.jpmc_coding_challenge.data.model.UIState
import com.example.jpmc_coding_challenge.di.viewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SchoolViewModel (private val repository: ApiRepositoryImpl): ViewModel() {

    private val _schoolData = MutableLiveData<UIState<*>>()
    val schoolData : LiveData<UIState<*>>

        get() = _schoolData

    private val _satData = MutableLiveData<UIState<*>>()
    val satData : LiveData<UIState<*>>
        get() = _satData

    init {
        getSchools()
    }

    fun getSchools() {
        CoroutineScope(Dispatchers.IO).launch {
            _schoolData.postValue(UIState.Loading)
            repository.fetchSchools()
                .catch {
                    _schoolData.postValue(UIState.Error("Failed to retrieve schools"))
                }
                .collect { _schoolData.postValue(it) }
        }
    }

    fun getSatScores() {
        CoroutineScope(Dispatchers.IO).launch {
            _satData.postValue(UIState.Loading)
            repository.fetchSatScores()
                .catch {
                    _satData.postValue(UIState.Error("Failed to retrieve SAT scores"))
                }
                .collect {
                    _satData.postValue(it)
                }
        }
    }
}