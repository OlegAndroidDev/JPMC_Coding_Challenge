package com.example.jpmc_coding_challenge.data.api

import com.example.jpmc_coding_challenge.data.model.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface ApiRepository {
    suspend fun fetchSchools(): Flow<UIState<*>>
    suspend fun fetchSatScores(): Flow<UIState<*>>
}

class ApiRepositoryImpl (private val service: ApiService) : ApiRepository {

    override suspend fun fetchSchools() = flow { emit(service.fetchSchools()) }
        .map { response ->
            response.body()?.let {
                UIState.Success(it)
            } ?: UIState.Error("Empty school response")
        }
        .flowOn(Dispatchers.IO)
        .conflate()

    override suspend fun fetchSatScores() = flow { emit(service.fetchSatScores()) }
        .map { response ->
            response.body()?.let {
                UIState.Success(it)
            } ?: UIState.Error("Empty SAT response")
        }
        .flowOn(Dispatchers.IO)
        .conflate()

}