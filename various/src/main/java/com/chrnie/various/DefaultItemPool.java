package com.chrnie.various;

import java.util.ArrayList;
import java.util.List;

public final class DefaultItemPool implements ItemPool {

  private List<Various.Bundle> bundleList;

  @Override public void init(List<Various.Bundle> bundleList) {
    this.bundleList = new ArrayList<>(bundleList);
  }

  @Override public int viewTypeOf(Class itemType) {
    for (int i = 0; i < bundleList.size(); i++) {
      Various.Bundle bundle = bundleList.get(i);
      if (itemType.equals(bundle.itemType)) {
        return i;
      }
    }
    throw new IllegalArgumentException();
  }

  @Override public Various.Bundle bundleOf(int viewType) {
    return bundleList.get(viewType);
  }
}
