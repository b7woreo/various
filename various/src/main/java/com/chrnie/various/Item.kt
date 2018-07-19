package com.chrnie.various

import android.support.v7.widget.RecyclerView

data class Item<in T, VH : RecyclerView.ViewHolder> internal constructor(
        val dataType: Class<in T>,
        val viewBinder: ViewBinder<T, VH>
)