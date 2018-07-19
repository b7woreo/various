package com.chrnie.various

import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.util.SparseArray

object DefaultItemMatcherFactory : ItemMatcher.Factory {

    private val dataTypeToIndex = ArrayMap<Class<*>, Int>()

    override fun create(itemList: List<Item<*, *>>): ItemMatcher {
        val indexToItem = SparseArray<ViewBinder<Any, RecyclerView.ViewHolder>>(itemList.size)
        itemList.forEach { (dataType, viewBinder) ->

            val index = (dataTypeToIndex[dataType]
                    ?: (dataTypeToIndex.size + 1).apply { dataTypeToIndex[dataType] = this })

            indexToItem.put(index, viewBinder as ViewBinder<Any, RecyclerView.ViewHolder>)
        }

        return DefaultItemMatcher(indexToItem)
    }

    private class DefaultItemMatcher(
            private val indexToItem: SparseArray<ViewBinder<Any, RecyclerView.ViewHolder>>
    ) : ItemMatcher {

        override fun requestViewType(date: Any): Int {
            val dataType = date.javaClass
            return dataTypeToIndex[dataType]
                    ?: throw  RuntimeException("Not found match item, make sure it has been registered: ${dataType.name}")
        }

        override fun requestViewHolderBinder(viewType: Int): ViewBinder<Any, RecyclerView.ViewHolder> {
            return indexToItem[viewType]
        }
    }
}