package com.chrnie.various

import android.view.LayoutInflater
import android.view.ViewGroup

abstract class GenericViewBinder<T> : ViewBinder<T, GenericViewHolder>() {

    abstract val layoutId: Int

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): GenericViewHolder {
        val itemView = inflater.inflate(layoutId, parent, false)
        return GenericViewHolder(itemView)
    }

}