package com.example.cattaskapp.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.cattaskapp.apidata.Cat

class CatDiffCallback: DiffUtil.ItemCallback<Cat>() {

    override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.idCat == newItem.urlCat
    }

    override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem == newItem
    }

    @Nullable
    @Override
    override fun getChangePayload(oldItem: Cat, newItem: Cat): Any? {
        return super.getChangePayload(oldItem, newItem)
    }

}