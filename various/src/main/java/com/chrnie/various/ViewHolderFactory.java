package com.chrnie.various;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public interface ViewHolderFactory<T, VH extends Various.ViewHolder<T>> {
    VH create(LayoutInflater inflater, ViewGroup container);
}
