package com.chrnie.various;

import com.chrnie.various.Various.OnBindCallback;
import com.chrnie.various.Various.OnCreateCallback;
import java.util.List;

public abstract class ItemMatcher {

  public abstract int getViewType(Class dateType);

  public abstract Item getItem(int viewType);

  final OnCreateCallback getOnCreateCallback(int viewType) {
    return getItem(viewType).onCreateCallback;
  }

  final OnBindCallback getOnBindCallback(int viewType) {
    return getItem(viewType).onBindCallback;
  }

  public interface Factory {

    ItemMatcher create(List<Item> itemList);
  }
}
