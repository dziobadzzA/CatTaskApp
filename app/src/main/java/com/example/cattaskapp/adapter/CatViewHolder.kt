package com.example.cattaskapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cattaskapp.R
import com.example.cattaskapp.apidata.ListenerAdapter

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