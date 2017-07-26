package com.chrnie.various;

import java.util.Comparator;
import java.util.List;

public final class BinarySearchItemPool implements ItemPool {

  private static final Comparator<Class> CLASS_COMPARATOR =
      (a, b) -> Integer.compare(System.identityHashCode(a), System.identityHashCode(b));

  private static final Comparator<Various.Bundle> BUNDLE_COMPARATOR =
      (a, b) -> CLASS_COMPARATOR.compare(a.itemType, b.itemType);

  private Various.Bundle[] bundles;

  @Override public void init(List<Various.Bundle> bundleList) {
    bundles = bundleList.toArray(new Various.Bundle[bundleList.size()]);
    sort();
  }

  @Override public int viewTypeOf(Class itemType) {
    int index = indexOf(itemType);
    if (itemType.equals(bundles[index].itemType)) return index;

    for (int i = index + 1; i < bundles.length; i++) {
      Class clz = bundles[i].itemType;
      int cmp = CLASS_COMPARATOR.compare(clz, itemType);
      if (cmp != 0) break;
      if (itemType.equals(clz)) return i;
    }

    for (int i = index - 1; i >= 0; i--) {
      Class clz = bundles[i].itemType;
      int cmp = CLASS_COMPARATOR.compare(clz, itemType);
      if (cmp != 0) break;
      if (itemType.equals(clz)) return i;
    }

    throw new IllegalArgumentException();
  }

  @Override public Various.Bundle bundleOf(int viewType) {
    return bundles[viewType];
  }

  private int indexOf(Class itemType) {
    int l = 0;
    int r = bundles.length - 1;

    while (r >= l) {
      int mid = (l + r) / 2;
      Class c = bundles[mid].itemType;
      int cmp = CLASS_COMPARATOR.compare(itemType, c);
      if (cmp == 0) {
        return mid;
      } else if (cmp > 0) {
        l = mid + 1;
      } else {
        r = mid - 1;
      }
    }

    return -1;
  }

  private void sort() {
    for (int i = 0; i < bundles.length - 1; i++) {
      for (int j = i + 1; j > 0; j--) {
        Various.Bundle a = bundles[j - 1];
        Various.Bundle b = bundles[j];
        if (BUNDLE_COMPARATOR.compare(a, b) <= 0) break;
        exchange(j - 1, j);
      }
    }
  }

  private void exchange(int i, int j) {
    Various.Bundle tmp = bundles[i];
    bundles[i] = bundles[j];
    bundles[j] = tmp;
  }
}
