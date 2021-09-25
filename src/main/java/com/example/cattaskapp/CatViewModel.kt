package com.example.cattaskapp


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cattaskapp.apidata.Cat
import com.example.cattaskapp.apidata.CatApiImpl
import com.example.cattaskapp.fragment.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CatViewModel: ViewModel() {

    val fragmentState: State = State("fragmentCat")
    private val _items = MutableLiveData<List<Cat>>()
    val items: LiveData<List<Cat>> get() = _items

    var sorted: String? = null

    private val _newItem = MutableLiveData<List<Cat>>()
    val newItem: LiveData<List<Cat>> = _newItem

    private val _connect = MutableLiveData<Boolean>()
    val connect: LiveData<Boolean> = _connect

    private lateinit var context:Context

    fun initContext(context: Context) {
        this.context = context
    }

    init {
        _connect.value = false
    }

    suspend fun setInitItems() {
        viewModelScope.launch {
            if (isOnline(context))
                _items.value = CatApiImpl.getListOfCats()
            else
                _items.value = listOf()
        }
    }

    suspend fun getNewAddItems() {
        GlobalScope.launch (Dispatchers.Main) {
            if (isOnline(context)) {
                _newItem.value = CatApiImpl.getListOfCats()
                _connect.value = _newItem.value!!.isEmpty()
            }
            else
                _newItem.value = listOf()
        }
    }

    fun getBundle(): String {
        var send = ""
        if (!sorted.isNullOrEmpty())
            send = sorted!!
        return send
    }

    fun openFragmentCat() {
        if (!fragmentState.checkState())
            fragmentState.updateState()
    }

    private fun isOnline(context: Context): Boolean {

        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }

}