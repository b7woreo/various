package com.chrnie.various;

import java.util.Comparator;
import java.util.List;

public final class BinarySearchItemPool implements ItemPool {

  private static final Comparator<Various.Bundle> BUNDLE_COMPARATOR = (a, b) -> {
    String n1 = a.itemType.getName();
    String n2 = b.itemType.getName();
    return n1.compareTo(n2);
  };

  private Various.Bundle[] bundles;

  @Override public void init(List<Various.Bundle> bundleList) {
    bundles = bundleList.toArray(new Various.Bundle[bundleList.size()]);
    sort(bundles);
  }

  @Override public int viewTypeOf(Class itemType) {
    String t = itemType.getName();

    int l = 0;
    int r = bundles.length - 1;

    while (r >= l) {
      int mid = (l + r) / 2;
      Class c = bundles[mid].itemType;
      if (c.equals(itemType)) {
        return mid;
      } else if (t.compareTo(c.getName()) > 0) {
        l = mid + 1;
      } else {
        r = mid - 1;
      }
    }

    throw new IllegalArgumentException();
  }

  @Override public Various.Bundle bundleOf(int viewType) {
    return bundles[viewType];
  }

  private static void sort(Various.Bundle[] bundles) {
    for (int i = 0; i < bundles.length - 1; i++) {
      for (int j = i + 1; j > 0; j--) {
        Various.Bundle a = bundles[j - 1];
        Various.Bundle b = bundles[j];
        if (BUNDLE_COMPARATOR.compare(a, b) <= 0) break;
        exchange(bundles, j - 1, j);
      }
    }
  }

  private static void exchange(Various.Bundle[] bundles, int i, int j) {
    Various.Bundle tmp = bundles[i];
    bundles[i] = bundles[j];
    bundles[j] = tmp;
  }
}
