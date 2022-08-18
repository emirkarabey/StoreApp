package com.example.storeapp.ui.home

import androidx.lifecycle.*
import com.example.storeapp.data.api.ApiFactory
import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.data.entity.Products
import com.example.storeapp.data.local.StoreDao
import com.example.storeapp.domain.mapper.StoreEntityMapper
import com.example.storeapp.repository.HomeRepositoryImpl
import com.example.storeapp.repository.StoreRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepositoryImpl,
    private val apiFactory: ApiFactory,
    private val dbRepository: StoreRepository
): ViewModel() {

    val productList: MutableLiveData<List<Products>> = MutableLiveData()
    val roomList: MutableLiveData<List<ProductEntity>> = MutableLiveData()
    fun getData(
    ) = viewModelScope.launch(Dispatchers.IO){
        productList.postValue(repository.getProducts())
    }

    fun addProduct(product: Products){//mapperla kullan
        viewModelScope.launch {
            dbRepository.addProduct(ProductEntity(title = product.title, price = product.price,
            category = product.category, description = product.description, image = product.image))
        }
    }

    fun getAllProductFromRoom(){
        viewModelScope.launch(Dispatchers.IO) {
            roomList.postValue(dbRepository.getAllProducts())
        }
    }
}