package com.example.cattaskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cattaskapp.R
import com.example.cattaskapp.apidata.Cat
import com.example.cattaskapp.apidata.ListenerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CatAdapter(private val listener:ListenerAdapter) : RecyclerView.Adapter<CatViewHolder>() {

    private val items = mutableListOf<Cat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item, null), listener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val fileName = items[position].idCat  ?: ""
        val imageUrl = items[position].urlCat ?: ""
        holder.bind(fileName, imageUrl)
    }

    fun addItems(newItems: List<Cat>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: CatViewHolder) {
        super.onViewAttachedToWindow(holder)

        if (holder.layoutPosition == items.size - 1) {

            GlobalScope.launch (Dispatchers.Main) {
                listener.getNewItem()
            }

        }

    }

}

class  CatViewHolder(view: View, private val listener: ListenerAdapter) : RecyclerView.ViewHolder(view) {

    private val textView = view.findViewById<TextView>(R.id.textView)
    private val imageView = view.findViewById<ImageView>(R.id.imageView)

    fun bind(filmName: String, imageUrl: String) {
        textView.text = filmName
        imageView.load(imageUrl)
        imageView.setOnClickListener {
            listener.openFragmentCat(imageUrl)
        }
    }

}