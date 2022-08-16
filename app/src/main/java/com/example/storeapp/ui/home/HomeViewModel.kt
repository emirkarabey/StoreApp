package com.example.storeapp.ui.home

import androidx.lifecycle.*
import com.example.storeapp.data.ApiFactory
import com.example.storeapp.data.Products
import com.example.storeapp.repository.HomeRepositoryImpl

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepositoryImpl,
    private val apiFactory: ApiFactory
): ViewModel() {

    val characterList: MutableLiveData<List<Products>> = MutableLiveData()

    fun getData(
    ) = viewModelScope.launch(Dispatchers.IO){
        characterList.postValue(repository.getProducts())
    }
}