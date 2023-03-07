package com.example.effecticetv.view

import LeaderApi.JsonElements.Photo
import LeaderApi.LeaderApi
import LeaderApi.PhotoJsonDeserializer
import LeaderApi.SearchResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListViewModel: ViewModel() {
    private var _apiResponse = MutableStateFlow<SearchResponse?>((null))
    val apiResponse = _apiResponse.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val builder = GsonBuilder()
            builder.registerTypeAdapter(Photo::class.java, PhotoJsonDeserializer())
            val retrofit = Retrofit.Builder()
                .baseUrl("https://leader-id.ru/")
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();
            val api = retrofit.create(LeaderApi::class.java)
            _apiResponse.update { api.searchEvents(10,3942).execute().body() }
        }
    }
}