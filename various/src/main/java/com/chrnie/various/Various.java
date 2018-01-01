package com.chrnie.various;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.chrnie.various.ViewHolder.Factory;
import java.util.ArrayList;
import java.util.List;

public final class Various {

  private Various() {
    throw new IllegalStateException("none constructor");
  }

  public static Builder of(List<?> dataList) {
    return Various.of(dataList, DefaultItemMatcherFactory.getInstance());
  }

  public static Builder of(List<?> dataList, ItemMatcher.Factory factory) {
    return new Builder(dataList, factory);
  }

  public interface OnCreateCallback<VH extends RecyclerView.ViewHolder> {

    VH onCreate(LayoutInflater inflater, ViewGroup container);
  }

  public interface OnBindCallback<VH extends RecyclerView.ViewHolder, T> {

    void onBind(VH holder, T data, List<Object> payloads);
  }

  public static class Builder {

    final List<?> dataList;
    final ItemMatcher.Factory factory;
    final List<Item> itemList = new ArrayList<>(2);

    Builder(List<?> dataList, ItemMatcher.Factory factory) {
      this.dataList = dataList;
      this.factory = factory;
    }

    public <VH extends RecyclerView.ViewHolder, T> Builder register(Class<T> dateType,
        OnCreateCallback<VH> onCreateCallback) {
      return register(dateType, onCreateCallback, null);
    }

    public <VH extends RecyclerView.ViewHolder, T> Builder register(Class<T> dateType,
        OnCreateCallback<VH> onCreateCallback,
        OnBindCallback<VH, T> onBindCallback) {
      itemList.add(new Item(dateType, onCreateCallback, onBindCallback));
      return this;
    }

    public <T, VH extends ViewHolder<T>> Builder register(Class<T> dateType,
        Factory<T, VH> factory) {
      return register(dateType, factory::create, ViewHolder::bind);
    }

    public RecyclerView.Adapter<RecyclerView.ViewHolder> build() {
      return new AdapterImpl(this);
    }
  }

  public interface ViewHolderCallback {

    default boolean onFailedToRecycleView() {
      return false;
    }

    default void onViewAttachedToWindow() {
    }

    default void onViewDetachedFromWindow() {
    }

    default void onViewRecycled() {
    }
  }
}
