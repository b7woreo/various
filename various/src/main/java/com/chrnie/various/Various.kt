package com.chrnie.various

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlin.reflect.KClass

object Various {

    class Builder<DATA : Any> @JvmOverloads constructor(private val itemMatcherFactory: ItemMatcher.Factory = DefaultItemMatcherFactory) {
        private val itemList = ArrayList<Item<*, *>>()

        fun <T : DATA, VH : RecyclerView.ViewHolder> register(
                dataType: KClass<in T>,
                viewHolderBinder: ViewHolderBinder<T, VH>
        ): Builder<DATA> {
            return register(dataType.java, viewHolderBinder)
        }

        fun <T : DATA, VH : RecyclerView.ViewHolder> register(
                dataType: Class<in T>,
                viewHolderBinder: ViewHolderBinder<T, VH>
        ): Builder<DATA> {
            itemList.add(Item(dataType, viewHolderBinder))
            return this
        }

        @JvmOverloads
        fun <T : DATA, VH : RecyclerView.ViewHolder> register(
                dataType: KClass<in T>,
                onCreateViewHolderCallback: OnCreateViewHolderCallback<VH>,
                onBindViewHolderCallback: OnBindViewHolderCallback<T, VH> = { _, _, _ -> }
        ): Builder<DATA> {
            return register(dataType.java, onCreateViewHolderCallback, onBindViewHolderCallback)
        }

        @JvmOverloads
        fun <T : DATA, VH : RecyclerView.ViewHolder> register(
                dataType: Class<in T>,
                onCreateViewHolderCallback: OnCreateViewHolderCallback<VH>,
                onBindViewHolderCallback: OnBindViewHolderCallback<T, VH> = { _, _, _ -> }
        ): Builder<DATA> {
            val binder = LambdaViewHolderBinder(onCreateViewHolderCallback, onBindViewHolderCallback)
            val item = Item(dataType, binder)
            itemList.add(item)
            return this
        }

        fun build(): Various.Adapter<DATA> {
            val itemMatcher = itemMatcherFactory.create(ArrayList(itemList))
            return Adapter(itemMatcher)
        }
    }

    class Adapter<DATA : Any> internal constructor(private val itemMatcher: ItemMatcher) :
            RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var dataList: List<DATA> = emptyList()

        override fun getItemCount(): Int = dataList.size

        override fun getItemViewType(position: Int): Int {
            val data = dataList[position]
            return itemMatcher.requestViewType(data)
        }

        override fun getItemId(position: Int): Long {
            val viewType = getItemViewType(position)
            val binder = itemMatcher.requestViewHolderBinder(viewType)
            val data = dataList[position]
            val id = binder.getItemId(data)
            return viewType.toLong() shl 32 or id.toLong()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binder = itemMatcher.requestViewHolderBinder(viewType)
            return binder.onCreateViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val data = dataList[position]
            val binder = itemMatcher.requestViewHolderBinder(holder.itemViewType)
            binder.onBindViewHolder(holder, data, null)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>?) {
            val data = dataList[position]
            val binder = itemMatcher.requestViewHolderBinder(holder.itemViewType)
            binder.onBindViewHolder(holder, data, payloads)
        }

        override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
            val binder = itemMatcher.requestViewHolderBinder(holder.itemViewType)
            binder.onViewAttachedToWindow(holder)
        }

        override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
            val binder = itemMatcher.requestViewHolderBinder(holder.itemViewType)
            binder.onViewDetachedFromWindow(holder)
        }

        override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
            val binder = itemMatcher.requestViewHolderBinder(holder.itemViewType)
            binder.onViewRecycled(holder)
        }

        override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
            val binder = itemMatcher.requestViewHolderBinder(holder.itemViewType)
            return binder.onFailedToRecycleView(holder)
        }
    }
}