package com.example.cattaskapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cattaskapp.adapter.CatAdapter
import com.example.cattaskapp.apidata.ListenerAdapter
import com.example.cattaskapp.apidata.ListenerFragment
import com.example.cattaskapp.databinding.ActivityMainBinding
import com.example.cattaskapp.fragment.FragmentCat
import com.example.cattaskapp.fragment.State
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_item.view.*


class MainActivity : AppCompatActivity(), ListenerAdapter, ListenerFragment  {

    private val itemAdapter = CatAdapter(this)
    private val catViewModel by viewModels<CatViewModel>()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var fragmentCat: FragmentCat = FragmentCat()
    private val fragmentState: State = State("fragmentCat")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(context)
        }

        catViewModel.items.observe(this, Observer {
            it ?: return@Observer
            itemAdapter.addItems(it)
        })

        catViewModel.newItem.observe(this, Observer {
            it ?: return@Observer
            itemAdapter.addItems(it)
        })

      //  initFragment()

    }


    override fun openFragmentCat(uri:String) {
        if (!fragmentState.checkState())
            fragmentState.updateState()
        runOpenFragment(fragmentCat, fragmentState)
        catViewModel.sorted = uri
    }

    override suspend fun getNewItem() {
        catViewModel.getNewAddItems()
    }

    override fun onBackPressed() {
        fragmentState.updateState()
        runOpenFragment(fragmentCat, fragmentState)
    }

    private fun runOpenFragment(fragment: Fragment, state: State) {

        val fragmentTr: FragmentTransaction = supportFragmentManager.beginTransaction()

        if (state.checkState())
            fragmentTr.replace(R.id.container, fragment, state.name)
        else
            fragmentTr.hide(fragment)

        fragmentTr.commit()
    }

    private fun initFragment(){
        supportFragmentManager.beginTransaction().add(R.id.container, fragmentCat, fragmentState.name).commit()
    }

    override fun getUri(): String {

        return if (catViewModel.sorted.isNullOrEmpty())
            ""
        else
            catViewModel.sorted!!

    }


}