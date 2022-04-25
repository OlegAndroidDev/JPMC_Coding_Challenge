package com.example.jpmc_coding_challenge.di

import com.example.jpmc_coding_challenge.data.api.ApiRepositoryImpl
import com.example.jpmc_coding_challenge.data.api.ApiService
import com.example.jpmc_coding_challenge.presentation.SchoolViewModel
import com.example.jpmc_coding_challenge.util.BASE_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel {
        SchoolViewModel(get())
    }
}

val repositoryModule = module {
    single {
        ApiRepositoryImpl(get())
    }
}

val apiModule = module {
    single { provideApiService(get()) }
}

val retrofitModule = module {
    single { provideRetrofit() }
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
