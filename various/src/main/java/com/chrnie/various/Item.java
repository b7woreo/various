package com.chrnie.various;

public final class Item {

  public final Class dateType;
  final Various.OnCreateListener onCreateListener;
  final Various.OnBindListener onBindListener;
  final Various.OnBindWithPayloadListener onBindWithPayloadListener;

  Item(Class dateType, Various.OnCreateListener onCreateListener,
      Various.OnBindListener onBindListener,
      Various.OnBindWithPayloadListener onBindWithPayloadListener) {
    this.dateType = dateType;
    this.onCreateListener = onCreateListener;
    this.onBindListener = onBindListener;
    this.onBindWithPayloadListener = onBindWithPayloadListener;
  }
}
