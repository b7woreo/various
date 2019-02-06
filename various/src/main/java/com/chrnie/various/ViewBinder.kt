package com.chrnie.various

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class ViewBinder<in T, VH : RecyclerView.ViewHolder> {

    open fun getItemId(data: T): Int {
        return RecyclerView.NO_ID.toInt()
    }

    abstract fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH

    abstract fun onBindViewHolder(holder: VH, data: T, payloads: List<Any>)

    open fun onViewAttachedToWindow(holder: VH) {}

    open fun onViewDetachedFromWindow(holder: VH) {}

    open fun onViewRecycled(holder: VH) {}

    open fun onFailedToRecycleView(holder: VH): Boolean {
        return false
    }
}