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

  public static Builder of(List<?> dataList) {
    return Various.of(dataList, new DefaultAlgorithmFactory());
  }

  public static Builder of(List<?> dataList, Algorithm.Factory factory) {
    return new Builder(dataList, factory);
  }

  public interface OnCreateListener<V extends ViewHolder> {

    V onCreate(LayoutInflater inflater, ViewGroup container);
  }

  public interface OnBindListener<V extends ViewHolder, T> {

    void onBind(V holder, T data);
  }

  public interface OnBindWithPayloadListener<V extends ViewHolder, T> {
    void onBindWithPayload(V holder, T data, List<Object> payloads);
  }

  public static class Builder {

    final List<?> dataList;
    final Algorithm.Factory factory;
    final List<Item> itemList = new ArrayList<>(2);

    Builder(List<?> dataList, Algorithm.Factory factory) {
      this.dataList = dataList;
      this.factory = factory;
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
      itemList.add(
          new Item(itemType, onCreateListener, onBindListener, onBindWithPayloadListener));
      return this;
    }

    public RecyclerView.Adapter<ViewHolder> build() {
      return new Adapter(this);
    }
  }

  static class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<?> dataList;
    private final Algorithm algorithm;

    Adapter(Builder builder) {
      this.dataList = builder.dataList;
      algorithm = builder.factory.create(builder.itemList);
    }

    @Override public int getItemViewType(int position) {
      Object data = dataList.get(position);
      Class itemType = getItemType(data);
      return algorithm.viewTypeOf(itemType);
    }

    private Class getItemType(Object item) {
      return item.getClass();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      OnCreateListener listener = algorithm.onCreateListenerOf(viewType);
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      return listener.onCreate(inflater, parent);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
      OnBindWithPayloadListener listener =
          algorithm.onBindWithPayloadListenerOf(holder.getItemViewType());

      if (!payloads.isEmpty() && listener != null) {
        Object data = dataList.get(position);
        listener.onBindWithPayload(holder, data, payloads);
      } else {
        onBindViewHolder(holder, position);
      }
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
      OnBindListener listener = algorithm.onBindListenerOf(holder.getItemViewType());

      if (listener != null) {
        Object data = dataList.get(position);
        listener.onBind(holder, data);
      }
    }

    @Override public int getItemCount() {
      return dataList.size();
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
