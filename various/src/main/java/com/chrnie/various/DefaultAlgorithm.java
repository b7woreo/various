package com.chrnie.various;

import java.util.ArrayList;
import java.util.List;

final class DefaultAlgorithm extends Algorithm {
  private List<Item> itemList;

  DefaultAlgorithm(List<Item> itemList) {
    this.itemList = new ArrayList<>(itemList);
  }

  @Override public int viewTypeOf(Class itemType) {
    for (int i = 0; i < itemList.size(); i++) {
      Item bundle = itemList.get(i);
      if (itemType.equals(bundle.itemType)) {
        return i;
      }
    }
    throw new ItemNotFoundException(itemType);
  }

  @Override public Item bundleOf(int viewType) {
    return itemList.get(viewType);
  }
}
