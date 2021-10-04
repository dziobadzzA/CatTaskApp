package com.example.cattaskapp

import android.content.Context
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test

class ViewModelTest {

    lateinit var viewModel: CatViewModel

    @Test
    fun `load data EXPECT repository`()  {

        val context = mockk<Context>(relaxed = true)

        GlobalScope.launch (Dispatchers.Main) {
            viewModel = CatViewModel()
            viewModel.initContext(context)
            viewModel.getNewAddItems()
            assertEquals(10, viewModel.connect.value)
        }

    }

}