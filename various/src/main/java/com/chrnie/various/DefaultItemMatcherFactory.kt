package com.chrnie.various

import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.util.SparseArray

object DefaultItemMatcherFactory : ItemMatcher.Factory {

    private val dataTypeToIndex = ArrayMap<Class<*>, Int>()

    override fun create(itemList: List<Item<*, *>>): ItemMatcher {
        val indexToItem = SparseArray<ViewHolderBinder<Any, RecyclerView.ViewHolder>>(itemList.size)
        itemList.forEach {
            val dataType = it.dataType

            val index = dataTypeToIndex[dataType]
                    ?: (dataTypeToIndex.size + 1).apply { dataTypeToIndex[dataType] = this }

            indexToItem.put(index, it.viewHolderBinder as ViewHolderBinder<Any, RecyclerView.ViewHolder>)
        }

        return DefaultItemMatcher(indexToItem)
    }

    class DefaultItemMatcher(
        private val indexToItem: SparseArray<ViewHolderBinder<Any, RecyclerView.ViewHolder>>
    ) : ItemMatcher {

        override fun requestViewType(date: Any): Int {
            val dataType = date.javaClass
            return dataTypeToIndex[dataType]
                    ?: throw  RuntimeException("Not found match item, make sure it has been registered: ${dataType.name}")
        }

        override fun requestViewHolderBinder(viewType: Int): ViewHolderBinder<Any, RecyclerView.ViewHolder> {
            return indexToItem[viewType]
        }
    }
}