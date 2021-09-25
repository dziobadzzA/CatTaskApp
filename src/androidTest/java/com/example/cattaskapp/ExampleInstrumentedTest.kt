package com.example.cattaskapp

import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cattaskapp.apidata.Cat
import com.example.cattaskapp.apidata.CatApiImpl
import com.example.cattaskapp.fragment.State
import kotlinx.coroutines.launch

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.nytimesapp", appContext.packageName)
    }

    @Test
    fun stateFragment(){
        val state = State("test")
        state.updateState()
        assertEquals(true, state.checkState())
    }

    @Test
    fun testListCat(){

        val catViewModel = CatViewModel()
        val a =  MutableLiveData<List<Cat>>()

        catViewModel.viewModelScope.launch {
            a.value = CatApiImpl.getListOfCats()
            assertEquals(10, a.value?.size)
        }

    }
}