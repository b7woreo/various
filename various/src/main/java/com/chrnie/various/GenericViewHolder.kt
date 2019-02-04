package com.chrnie.various

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GenericViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewIdCache = SparseArray<View>()

    @Suppress("UNCHECKED_CAST")
    fun <VIEW: View> requestViewById(id: Int): VIEW {
        var result = viewIdCache[id]

        if(result == null){
            result = itemView.findViewById(id)
            viewIdCache.put(id, result!!)
        }

        return result as VIEW
    }
}