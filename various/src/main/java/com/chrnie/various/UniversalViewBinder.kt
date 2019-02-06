package com.chrnie.various

import android.view.LayoutInflater
import android.view.ViewGroup

abstract class UniversalViewBinder<T> : ViewBinder<T, UniversalViewHolder>() {

    abstract val layoutId: Int

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): UniversalViewHolder {
        val itemView = inflater.inflate(layoutId, parent, false)
        return UniversalViewHolder(itemView)
    }

}