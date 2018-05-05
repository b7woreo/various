package com.chrnie.various.databinding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chrnie.various.ViewHolderBinder

class DataBindingViewHolderBinder<in T>(private val layoutId: Int, private val variableId: Int)
    : ViewHolderBinder<T, DataBindingViewHolderBinder.DataBindingViewHolder>() {


    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): DataBindingViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, data: T, payloads: List<Any>) {
        holder.binding.setVariable(variableId, data)
    }


    class DataBindingViewHolder(internal val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}