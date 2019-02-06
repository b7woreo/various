package com.chrnie.various

import android.util.SparseArray
import androidx.collection.ArrayMap
import androidx.recyclerview.widget.RecyclerView

object DefaultItemMatcherFactory : ItemMatcher.Factory {

    private val dataTypeToIndex = ArrayMap<Class<*>, Int>()

    override fun create(itemList: List<Item<*, *>>): ItemMatcher {
        val indexToBinder = SparseArray<ViewBinder<Any, RecyclerView.ViewHolder>>(itemList.size)
        itemList.forEach { (dataType, viewBinder) ->

            val index = (dataTypeToIndex[dataType]
                    ?: (dataTypeToIndex.size + 1).also { dataTypeToIndex[dataType] = it })

            indexToBinder.put(index, viewBinder as ViewBinder<Any, RecyclerView.ViewHolder>)
        }

        return DefaultItemMatcher(indexToBinder)
    }

    private class DefaultItemMatcher(
        private val indexToBinder: SparseArray<ViewBinder<Any, RecyclerView.ViewHolder>>
    ) : ItemMatcher {

        override fun requestViewType(date: Any): Int {
            val dataType = date.javaClass
            return dataTypeToIndex[dataType]
                    ?: throw  RuntimeException("Not found match item, make sure it has been registered: ${dataType.name}")
        }

        override fun requestViewHolderBinder(viewType: Int): ViewBinder<Any, RecyclerView.ViewHolder> {
            return indexToBinder[viewType]
        }
    }
}