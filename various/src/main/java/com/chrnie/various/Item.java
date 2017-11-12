package com.chrnie.various;

import com.chrnie.various.Various.OnBindCallback;
import com.chrnie.various.Various.OnCreateCallback;

public final class Item {

  public final Class dateType;
  final OnCreateCallback onCreateCallback;
  final OnBindCallback onBindCallback;

  Item(Class dateType, OnCreateCallback onCreateCallback, OnBindCallback onBindCallback) {
    this.dateType = dateType;
    this.onCreateCallback = onCreateCallback;
    this.onBindCallback = onBindCallback;
  }
}
