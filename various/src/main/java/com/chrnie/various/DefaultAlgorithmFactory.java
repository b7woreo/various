package com.chrnie.various;

import java.util.ArrayList;
import java.util.List;

public final class DefaultAlgorithmFactory extends Algorithm.Factory {

  @Override
  public Algorithm create(List<Item> itemList) {
    return new DefaultAlgorithm(itemList);
  }

  static final class DefaultAlgorithm extends Algorithm {

    private List<Item> itemList;

    DefaultAlgorithm(List<Item> itemList) {
      this.itemList = new ArrayList<>(itemList);
    }

    @Override
    public int viewTypeOf(Class dateType) {
      for (int i = 0; i < itemList.size(); i++) {
        Item bundle = itemList.get(i);
        if (dateType.equals(bundle.dateType)) {
          return i;
        }
      }
      throw new ItemNotFoundException(dateType);
    }

    @Override
    public Item itemOf(int viewType) {
      return itemList.get(viewType);
    }
  }
}
