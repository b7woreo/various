package com.chrnie.various;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chrnie.various.Various.ViewHolderCallback;
import java.util.List;

public abstract class ViewHolder<T> extends RecyclerView.ViewHolder implements ViewHolderCallback {

  public ViewHolder(View itemView) {
    super(itemView);
  }

  protected abstract void bind(T date, List<Object> payloads);

  public Context getContext() {
    return itemView.getContext();
  }

  public interface Factory<T, VH extends ViewHolder<T>> {

    VH create(LayoutInflater inflater, ViewGroup container);
  }
}
