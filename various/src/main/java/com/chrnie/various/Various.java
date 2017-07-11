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

  public static class Builder {

    private static final OnBindListener EMPTY_BIND_LISTENER = (holder, item) -> {
    };

    final List<?> itemList;
    final List<Bundle> bundleList = new ArrayList<>(2);

    Builder(List<?> itemList) {
      this.itemList = itemList;
    }

    public <V extends ViewHolder, T> Builder register(Class<T> itemType,
        OnCreateListener<V> onCreateListener, OnBindListener<V, T> onBindListener) {
      bundleList.add(new Bundle(itemType, onCreateListener, onBindListener));
      return this;
    }

    public <V extends ViewHolder, T> Builder register(Class<T> itemType,
        OnCreateListener<V> onCreateListener) {
      return register(itemType, onCreateListener, EMPTY_BIND_LISTENER);
    }

    public RecyclerView.Adapter<ViewHolder> build() {
      return new Adapter(this);
    }
  }

  static class Bundle {

    final Class itemType;
    final OnCreateListener onCreateListener;
    final OnBindListener onBindListener;

    Bundle(Class itemType, OnCreateListener onCreateListener, OnBindListener onBindListener) {
      this.itemType = itemType;
      this.onCreateListener = onCreateListener;
      this.onBindListener = onBindListener;
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

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
      Object item = itemList.get(position);
      Bundle bundle = bundleList.get(holder.getItemViewType());
      bundle.onBindListener.onBind(holder, item);
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
