package com.chrnie.various;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.chrnie.various.Various.Builder;
import com.chrnie.various.Various.OnBindCallback;
import com.chrnie.various.Various.OnCreateCallback;
import java.util.Collections;
import java.util.List;

class Adapter extends RecyclerView.Adapter<ViewHolder> {

  private final List<?> dataList;
  private final ItemMatcher itemMatcher;

  Adapter(Builder builder) {
    this.dataList = builder.dataList;
    itemMatcher = builder.factory.create(builder.itemList);
  }

  @Override
  public int getItemViewType(int position) {
    Object data = dataList.get(position);
    Class itemType = getItemType(data);
    return itemMatcher.getViewType(itemType);
  }

  private Class getItemType(Object item) {
    return item.getClass();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    OnCreateCallback listener = itemMatcher.getOnCreateCallback(viewType);
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return listener.onCreate(inflater, parent);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
    OnBindCallback listener = itemMatcher.getOnBindCallback(holder.getItemViewType());
    Object date = dataList.get(position);
    listener.onBind(holder, date, payloads);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    OnBindCallback listener = itemMatcher.getOnBindCallback(holder.getItemViewType());
    Object date = dataList.get(position);
    listener.onBind(holder, date, Collections.emptyList());
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }

  @Override
  public boolean onFailedToRecycleView(ViewHolder holder) {
    return holder instanceof Various.ViewHolder
        && ((Various.ViewHolder) holder).onFailedToRecycleView();
  }

  @Override
  public void onViewAttachedToWindow(ViewHolder holder) {
    if (holder instanceof Various.ViewHolder) {
      ((Various.ViewHolder) holder).onViewAttachedToWindow();
    }
  }

  @Override
  public void onViewDetachedFromWindow(ViewHolder holder) {
    if (holder instanceof Various.ViewHolder) {
      ((Various.ViewHolder) holder).onViewDetachedFromWindow();
    }
  }

  @Override
  public void onViewRecycled(ViewHolder holder) {
    if (holder instanceof Various.ViewHolder) {
      ((Various.ViewHolder) holder).onViewRecycled();
    }
  }
}
