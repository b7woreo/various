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
    return new Builder(itemList);
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
    final List<Bundle> bundleList = new ArrayList<>(2);

    Builder(List<?> itemList) {
      this.itemList = itemList;
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
    private final List<Bundle> bundleList;

    Adapter(Builder builder) {
      this.itemList = builder.itemList;
      bundleList = new ArrayList<>(builder.bundleList);
    }

    @Override public int getItemViewType(int position) {
      Class<?> itemType = itemList.get(position).getClass();
      for (int i = 0; i < bundleList.size(); i++) {
        Bundle bundle = bundleList.get(i);
        if (itemType.equals(bundle.itemType)) {
          return i;
        }
      }
      throw new IllegalArgumentException();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      Bundle bundle = bundleList.get(viewType);
      return bundle.onCreateListener.onCreate(inflater, parent);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
      Bundle bundle = bundleList.get(holder.getItemViewType());

      if (!payloads.isEmpty() && bundle.onBindWithPayloadListener != null) {
        Object item = itemList.get(position);
        bundle.onBindWithPayloadListener.onBindWithPayload(holder, item, payloads);
      } else if (bundle.onBindListener != null) {
        super.onBindViewHolder(holder, position, payloads);
      }
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
      Bundle bundle = bundleList.get(holder.getItemViewType());

      if (bundle.onBindListener != null) {
        Object item = itemList.get(position);
        bundle.onBindListener.onBind(holder, item);
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
