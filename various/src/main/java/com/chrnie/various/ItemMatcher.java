package com.chrnie.various;

import java.util.List;

public abstract class ItemMatcher {

  public abstract int viewTypeOf(Class dateType);

  public abstract Item itemOf(int viewType);

  final Various.OnCreateListener onCreateListenerOf(int viewType) {
    return itemOf(viewType).onCreateListener;
  }

  final Various.OnBindListener onBindListenerOf(int viewType) {
    return itemOf(viewType).onBindListener;
  }

  final Various.OnBindWithPayloadListener onBindWithPayloadListenerOf(int viewType) {
    return itemOf(viewType).onBindWithPayloadListener;
  }

  public static abstract class Factory {

    public abstract ItemMatcher create(List<Item> itemList);
  }
}
