package com.chrnie.various;

import java.util.List;

public interface ItemPool {

  void init(List<Various.Bundle> bundleList);

  int viewTypeOf(Class itemType);

  Various.OnCreateListener onCreateOf(int viewType);

  Various.OnBindListener onBindListenerOf(int viewType);

  Various.OnBindWithPayloadListener onBindWithPayloadListenerOf(int viewType);
}
