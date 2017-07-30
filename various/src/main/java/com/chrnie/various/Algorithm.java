package com.chrnie.various;

import java.util.List;

public abstract class Algorithm {

  abstract int viewTypeOf(Class itemType);

  abstract Item bundleOf(int viewType);

  final Various.OnCreateListener onCreateListenerOf(int viewType) {
    return bundleOf(viewType).onCreateListener;
  }

  final Various.OnBindListener onBindListenerOf(int viewType) {
    return bundleOf(viewType).onBindListener;
  }

  final Various.OnBindWithPayloadListener onBindWithPayloadListenerOf(int viewType) {
    return bundleOf(viewType).onBindWithPayloadListener;
  }

  public static abstract class Factory {
    abstract Algorithm create(List<Item> itemList);
  }
}
