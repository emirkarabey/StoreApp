package com.example.storeapp.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.data.entity.Products
import com.example.storeapp.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dbRepository: StoreRepository,
): ViewModel() {
    val favList: MutableLiveData<List<ProductEntity>> = MutableLiveData()
    fun getAllFavoriteFromRoom(){
        viewModelScope.launch(Dispatchers.IO) {
            favList.postValue(dbRepository.getAllFavorites())
        }
    }

    fun deleteFavorite(productId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteFavorite(productId)
        }
    }
}