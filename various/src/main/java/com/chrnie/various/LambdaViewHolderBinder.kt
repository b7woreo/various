package com.chrnie.various

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class LambdaViewHolderBinder<in T, VH : RecyclerView.ViewHolder> internal constructor(
    private val onCreateViewHolderCallback: OnCreateViewHolderCallback<VH>,
    private val onBindViewHolderCallback: OnBindViewHolderCallback<T, VH>
) : ViewHolderBinder<T, VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH =
        onCreateViewHolderCallback(inflater, parent)

    override fun onBindViewHolder(holder: VH, data: T, payloads: List<Any>?) =
        onBindViewHolderCallback(holder, data, payloads)
}

typealias OnCreateViewHolderCallback<VH> = (LayoutInflater, ViewGroup) -> VH
typealias OnBindViewHolderCallback<T, VH> = (VH, T, List<Any>?) -> Unit