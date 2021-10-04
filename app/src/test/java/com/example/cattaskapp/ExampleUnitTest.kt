package com.example.cattaskapp

import androidx.lifecycle.MutableLiveData
import com.example.cattaskapp.apidata.Cat
import com.example.cattaskapp.apidata.CatApiImpl
import com.example.cattaskapp.fragment.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun stateFragment(){
        val state = State("test")
        state.updateState()
        assertEquals(true, state.checkState())
    }

    @Test
    fun testListCat(){
        val a =  MutableLiveData<List<Cat>>()
        GlobalScope.launch (Dispatchers.Main) {
            a.value = CatApiImpl.getListOfCats()
            assertEquals(10, a.value?.size)
        }
    }
}