package com.chrnie.various

import androidx.recyclerview.widget.RecyclerView

interface ItemMatcher {

    fun requestViewType(date: Any): Int

    fun requestViewHolderBinder(viewType: Int): ViewBinder<Any, RecyclerView.ViewHolder>

    interface Factory {
        fun create(itemList: List<Item<*, *>>): ItemMatcher
    }
}