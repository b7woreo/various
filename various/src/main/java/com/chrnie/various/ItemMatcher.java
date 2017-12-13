package com.chrnie.various;

import java.util.List;

public interface ItemMatcher {

  int getViewType(Object date);

  Item getItem(int viewType);

  interface Factory {

    ItemMatcher create(List<Item> itemList);
  }
}
