package com.chrnie.various;

import android.support.annotation.NonNull;
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

  @NonNull
  public static Builder of(@NonNull List<?> dataList) {
    return Various.of(dataList, DefaultItemMatcherFactory.getInstance());
  }

  @NonNull
  public static Builder of(@NonNull List<?> dataList, @NonNull ItemMatcher.Factory factory) {
    return new Builder(dataList, factory);
  }

  public interface OnCreateCallback<VH extends RecyclerView.ViewHolder> {

    @NonNull
    VH onCreate(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);
  }

  public interface OnBindCallback<VH extends RecyclerView.ViewHolder, T> {

    void onBind(@NonNull VH holder, @NonNull T data, @NonNull List<Object> payloads);
  }

  public static class Builder {

    final List<?> dataList;
    final ItemMatcher.Factory factory;
    final List<Item> itemList = new ArrayList<>(2);

    Builder(List<?> dataList, ItemMatcher.Factory factory) {
      this.dataList = dataList;
      this.factory = factory;
    }

    @NonNull
    public <VH extends RecyclerView.ViewHolder, T> Builder register(
        @NonNull Class<T> dateType,
        @NonNull OnCreateCallback<VH> onCreateCallback) {
      return register(dateType, onCreateCallback, (holder, data, payloads) -> {
        // empty;
      });
    }

    @NonNull
    public <VH extends RecyclerView.ViewHolder, T> Builder register(
        @NonNull Class<T> dateType,
        @NonNull OnCreateCallback<VH> onCreateCallback,
        @NonNull OnBindCallback<VH, ? super T> onBindCallback) {
      itemList.add(new Item(dateType, onCreateCallback, onBindCallback));
      return this;
    }

    @NonNull
    public <T> Builder register(
        @NonNull Class<T> dateType,
        @NonNull Factory<? super T, ? extends ViewHolder<? super T>> factory) {
      return register(dateType, factory::create, ViewHolder::bind);
    }

    @NonNull
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
