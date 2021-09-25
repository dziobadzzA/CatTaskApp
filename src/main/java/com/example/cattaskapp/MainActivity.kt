package com.example.cattaskapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cattaskapp.adapter.CatAdapter
import com.example.cattaskapp.apidata.ListenerAdapter
import com.example.cattaskapp.databinding.ActivityMainBinding
import com.example.cattaskapp.fragment.FragmentCat
import com.example.cattaskapp.fragment.State
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.layout_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ListenerAdapter  {

    private val itemAdapter = CatAdapter(this)
    private val catViewModel by viewModels<CatViewModel>()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var fragmentCat: FragmentCat = FragmentCat()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        catViewModel.initContext(applicationContext)

        GlobalScope.launch (Dispatchers.Main){
            catViewModel.setInitItems()
        }

        recyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(context)
        }


        catViewModel.connect.observe(this, Observer {
            stateNotInternet(it)
        })

        binding.review.setOnClickListener {
            GlobalScope.launch (Dispatchers.Main) {
                getNewItem()
            }
        }

        catViewModel.items.observe(this, Observer {
            it ?: return@Observer
            if (it.isNotEmpty()){
                itemAdapter.addItems(it)
            }
            else {
                stateNotInternet(true)
            }

        })

        catViewModel.newItem.observe(this, Observer {
            it ?: return@Observer
            itemAdapter.addItems(it)
        })



    }

    override fun openFragmentCat(url:String) {
        catViewModel.openFragmentCat()
        runOpenFragment(fragmentCat, catViewModel.fragmentState)
        catViewModel.sorted = url
        sendUri()
    }

    override suspend fun getNewItem() {
        catViewModel.getNewAddItems()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 0) {
            catViewModel.fragmentState.updateState()
            runOpenFragment(fragmentCat, catViewModel.fragmentState)
        }
        else {
            super.onBackPressed()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding  = null
    }

    private fun runOpenFragment(fragment: Fragment, state: State) {

        val fragmentTr: FragmentTransaction = supportFragmentManager.beginTransaction()

        if (state.checkState()) {
            fragmentTr.setCustomAnimations(R.animator.grow_from_middle, R.animator.shrink_to_middle)
            fragmentTr.replace(R.id.container, fragment, state.name)
        }
        else {

            if (supportFragmentManager.findFragmentByTag(state.name) is FragmentCat) {
                val fragment: FragmentCat = supportFragmentManager.findFragmentByTag(state.name) as FragmentCat
                supportFragmentManager.beginTransaction().remove(fragment).commit()
            }

        }

        fragmentTr.commit()
    }


    private fun sendUri(){
        val bundle = Bundle()
        bundle.putString(applicationContext.resources.getString(R.string.keyBundle), catViewModel.getBundle())
        fragmentCat.arguments = bundle
    }

    private fun stateNotInternet(yes: Boolean) {
        binding.review.isVisible = yes
        binding.review.isClickable = yes
    }


}