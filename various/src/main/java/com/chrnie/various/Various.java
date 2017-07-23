package com.chrnie.various;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public final class Various {

  private Various() {
    throw new IllegalStateException("none constructor");
  }

  public static Builder of(List<?> itemList) {
    return Various.of(itemList, new DefaultItemPool());
  }

  public static Builder of(List<?> itemList, ItemPool itemPool) {
    return new Builder(itemList, itemPool);
  }

  public interface OnCreateListener<V extends ViewHolder> {

    V onCreate(LayoutInflater inflater, ViewGroup container);
  }

  public interface OnBindListener<V extends ViewHolder, T> {

    void onBind(V holder, T item);
  }

  public interface OnBindWithPayloadListener<V extends ViewHolder, T> {
    void onBindWithPayload(V holder, T item, List<Object> payloads);
  }

  public static class Builder {

    final List<?> itemList;
    final ItemPool itemPool;
    final List<Bundle> bundleList = new ArrayList<>(2);

    Builder(List<?> itemList, ItemPool itemPool) {
      this.itemList = itemList;
      this.itemPool = itemPool;
    }

    public <V extends ViewHolder, T> Builder register(Class<T> itemType,
        OnCreateListener<V> onCreateListener) {
      return register(itemType, onCreateListener, null);
    }

    public <V extends ViewHolder, T> Builder register(Class<T> itemType,
        OnCreateListener<V> onCreateListener, OnBindListener<V, T> onBindListener) {
      return register(itemType, onCreateListener, onBindListener, null);
    }

    public <V extends ViewHolder, T> Builder register(Class<T> itemType,
        OnCreateListener<V> onCreateListener, OnBindListener<V, T> onBindListener,
        OnBindWithPayloadListener<V, T> onBindWithPayloadListener) {
      bundleList.add(
          new Bundle(itemType, onCreateListener, onBindListener, onBindWithPayloadListener));
      return this;
    }

    public RecyclerView.Adapter<ViewHolder> build() {
      return new Adapter(this);
    }
  }

  static class Bundle {

    final Class itemType;
    final OnCreateListener onCreateListener;
    final OnBindListener onBindListener;
    final OnBindWithPayloadListener onBindWithPayloadListener;

    Bundle(Class itemType, OnCreateListener onCreateListener, OnBindListener onBindListener,
        OnBindWithPayloadListener onBindWithPayloadListener) {
      this.itemType = itemType;
      this.onCreateListener = onCreateListener;
      this.onBindListener = onBindListener;
      this.onBindWithPayloadListener = onBindWithPayloadListener;
    }
  }

  static class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<?> itemList;
    private final ItemPool itemPool;

    Adapter(Builder builder) {
      this.itemList = builder.itemList;
      itemPool = builder.itemPool;
      itemPool.init(builder.bundleList);
    }

    @Override public int getItemViewType(int position) {
      Class<?> itemType = itemList.get(position).getClass();
      return itemPool.viewTypeOf(itemType);
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      OnCreateListener listener = itemPool.onCreateOf(viewType);
      return listener.onCreate(inflater, parent);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
      OnBindWithPayloadListener listener =
          itemPool.onBindWithPayloadListenerOf(holder.getItemViewType());

      if (!payloads.isEmpty() && listener != null) {
        Object item = itemList.get(position);
        listener.onBindWithPayload(holder, item, payloads);
      } else {
        super.onBindViewHolder(holder, position, payloads);
      }
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
      OnBindListener listener = itemPool.onBindListenerOf(holder.getItemViewType());

      if (listener != null) {
        Object item = itemList.get(position);
        listener.onBind(holder, item);
      }
    }

    @Override public int getItemCount() {
      return itemList.size();
    }

    @Override public boolean onFailedToRecycleView(ViewHolder holder) {
      return holder instanceof LifecycleViewHolder
          && ((LifecycleViewHolder) holder).onFailedToRecycleView();
    }

    @Override public void onViewAttachedToWindow(ViewHolder holder) {
      if (holder instanceof LifecycleViewHolder) {
        ((LifecycleViewHolder) holder).onViewAttachedToWindow();
      }
    }

    @Override public void onViewDetachedFromWindow(ViewHolder holder) {
      if (holder instanceof LifecycleViewHolder) {
        ((LifecycleViewHolder) holder).onViewDetachedFromWindow();
      }
    }

    @Override public void onViewRecycled(ViewHolder holder) {
      if (holder instanceof LifecycleViewHolder) {
        ((LifecycleViewHolder) holder).onViewRecycled();
      }
    }
  }

  public static abstract class LifecycleViewHolder extends RecyclerView.ViewHolder {
    public LifecycleViewHolder(View itemView) {
      super(itemView);
    }

    public boolean onFailedToRecycleView() {
      return false;
    }

    public void onViewAttachedToWindow() {

    }

    public void onViewDetachedFromWindow() {

    }

    public void onViewRecycled() {

    }
  }
}
