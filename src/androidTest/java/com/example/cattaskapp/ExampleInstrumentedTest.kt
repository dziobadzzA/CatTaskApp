package com.example.cattaskapp


import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cattaskapp.apidata.Cat
import com.example.cattaskapp.apidata.CatApiImpl
import com.example.cattaskapp.fragment.State
import io.mockk.every
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
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

        val a =  MutableLiveData<List<Cat>>()

        GlobalScope.launch (Dispatchers.Main) {
            a.value = CatApiImpl.getListOfCats()
            assertEquals(10, a.value?.size)
        }

    }

    @Test
    fun testConnectNet() {

        val appContext = InstrumentationRegistry.getInstrumentation().context

        GlobalScope.launch(Dispatchers.Main) {
            val viewModel = CatViewModel()
            viewModel.initContext(appContext)

            viewModel?.initContext(appContext)
            viewModel?.getNewAddItems()

           // Mockito.`when`(viewModel.sorted).thenReturn(viewModel.sorted)

            assertEquals(true, viewModel?.connect?.value)

        }

    }

}