package com.chrnie.various;

import java.util.List;

public abstract class Algorithm {

  abstract void init(List<Various.Bundle> bundleList);

  abstract int viewTypeOf(Class itemType);

  abstract Various.Bundle bundleOf(int viewType);

  final Various.OnCreateListener onCreateListenerOf(int viewType) {
    return bundleOf(viewType).onCreateListener;
  }

  final Various.OnBindListener onBindListenerOf(int viewType) {
    return bundleOf(viewType).onBindListener;
  }

  final Various.OnBindWithPayloadListener onBindWithPayloadListenerOf(int viewType) {
    return bundleOf(viewType).onBindWithPayloadListener;
  }
}
