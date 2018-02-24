package com.chrnie.various;

import android.support.annotation.NonNull;
import com.chrnie.various.Various.OnBindCallback;
import com.chrnie.various.Various.OnCreateCallback;

public final class Item {

  @NonNull
  public final Class dateType;

  @NonNull
  final OnCreateCallback onCreateCallback;

  @NonNull
  final OnBindCallback onBindCallback;

  Item(@NonNull Class dateType, @NonNull OnCreateCallback onCreateCallback, @NonNull OnBindCallback onBindCallback) {
    this.dateType = dateType;
    this.onCreateCallback = onCreateCallback;
    this.onBindCallback = onBindCallback;
  }
}
