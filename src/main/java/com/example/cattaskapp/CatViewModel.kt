package com.example.cattaskapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cattaskapp.apidata.Cat
import com.example.cattaskapp.apidata.CatApiImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CatViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Cat>>()
    val items: LiveData<List<Cat>> get() = _items

    var sorted: String? = null

    private val _newItem = MutableLiveData<List<Cat>>()
    val newItem: LiveData<List<Cat>> = _newItem

    init {
        viewModelScope.launch {
            _items.value = CatApiImpl.getListOfCats()
        }
    }

    suspend fun getNewAddItems() {

        GlobalScope.launch (Dispatchers.Main) {
            _newItem.value = CatApiImpl.getListOfCats()
        }

    }

}