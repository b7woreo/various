package com.chrnie.various;

public final class Item {
  final Class itemType;
  final Various.OnCreateListener onCreateListener;
  final Various.OnBindListener onBindListener;
  final Various.OnBindWithPayloadListener onBindWithPayloadListener;

  Item(Class itemType, Various.OnCreateListener onCreateListener,
      Various.OnBindListener onBindListener,
      Various.OnBindWithPayloadListener onBindWithPayloadListener) {
    this.itemType = itemType;
    this.onCreateListener = onCreateListener;
    this.onBindListener = onBindListener;
    this.onBindWithPayloadListener = onBindWithPayloadListener;
  }
}
