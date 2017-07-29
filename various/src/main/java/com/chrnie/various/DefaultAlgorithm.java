package com.chrnie.various;

import java.util.ArrayList;
import java.util.List;

final class DefaultAlgorithm extends Algorithm {
  private List<Various.Bundle> bundleList;

  DefaultAlgorithm(List<Various.Bundle> bundleList) {
    this.bundleList = new ArrayList<>(bundleList);
  }

  @Override public int viewTypeOf(Class itemType) {
    for (int i = 0; i < bundleList.size(); i++) {
      Various.Bundle bundle = bundleList.get(i);
      if (itemType.equals(bundle.itemType)) {
        return i;
      }
    }
    throw new ItemNotFoundException(itemType);
  }

  @Override public Various.Bundle bundleOf(int viewType) {
    return bundleList.get(viewType);
  }
}
