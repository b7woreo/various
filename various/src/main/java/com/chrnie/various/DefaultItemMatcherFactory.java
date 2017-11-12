package com.chrnie.various;

import java.util.ArrayList;
import java.util.List;

public final class DefaultItemMatcherFactory implements ItemMatcher.Factory {

  @Override
  public ItemMatcher create(List<Item> itemList) {
    return new DefaultItemMatcher(itemList);
  }

  static final class DefaultItemMatcher extends ItemMatcher {

    private List<Item> itemList;

    DefaultItemMatcher(List<Item> itemList) {
      this.itemList = new ArrayList<>(itemList);
    }

    @Override
    public int getViewType(Class dateType) {
      for (int i = 0; i < itemList.size(); i++) {
        Item bundle = itemList.get(i);
        if (dateType.equals(bundle.dateType)) {
          return i;
        }
      }
      throw new RuntimeException(
          String.format("%s not found match item, make sure it has been registered",
              dateType.getName()));
    }

    @Override
    public Item getItem(int viewType) {
      return itemList.get(viewType);
    }
  }
}
