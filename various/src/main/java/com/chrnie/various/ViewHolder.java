package com.chrnie.various;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chrnie.various.Various.ViewHolderCallback;
import java.util.List;

public abstract class ViewHolder<T> extends RecyclerView.ViewHolder implements ViewHolderCallback {

  public ViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  protected abstract void bind(@NonNull T date, @NonNull List<Object> payloads);

  @NonNull
  public Context getContext() {
    return itemView.getContext();
  }

  public interface Factory<T, VH extends ViewHolder<T>> {

    @NonNull
    VH create(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);
  }
}
