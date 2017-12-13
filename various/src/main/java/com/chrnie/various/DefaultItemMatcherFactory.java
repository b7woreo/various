package com.chrnie.various;

import android.support.v4.util.ArrayMap;
import android.util.SparseArray;
import java.util.List;

public final class DefaultItemMatcherFactory implements ItemMatcher.Factory {

  private static final DefaultItemMatcherFactory INSTANCE = new DefaultItemMatcherFactory();

  public static DefaultItemMatcherFactory getInstance() {
    return INSTANCE;
  }

  private DefaultItemMatcherFactory() {

  }

  @Override
  public ItemMatcher create(List<Item> itemList) {
    return new DefaultItemMatcher(itemList);
  }

  static final class DefaultItemMatcher implements ItemMatcher {

    private static final ArrayMap<Class, Integer> CLASS_OF_INDEX = new ArrayMap<>(8);

    private final SparseArray<Item> indexOfItem;

    DefaultItemMatcher(List<Item> itemList) {
      indexOfItem = new SparseArray<>(itemList.size());
      for (Item item : itemList) {
        Class clz = item.dateType;

        Integer index = CLASS_OF_INDEX.get(clz);
        if (index == null) {
          index = CLASS_OF_INDEX.size() + 1;
          CLASS_OF_INDEX.put(clz, index);
        }

        indexOfItem.put(index, item);
      }
    }

    @Override
    public int getViewType(Object date) {
      Class clz = date.getClass();

      Integer index = CLASS_OF_INDEX.get(clz);
      if (index == null) {
        throw new RuntimeException(
            String.format("%s not found match item, make sure it has been registered", clz.getName()));
      }

      return index;
    }

    @Override
    public Item getItem(int viewType) {
      return indexOfItem.get(viewType);
    }
  }
}
