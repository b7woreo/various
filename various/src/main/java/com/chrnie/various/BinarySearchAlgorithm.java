package com.chrnie.various;

import java.util.List;

public final class BinarySearchAlgorithm extends Algorithm {

  private int[] hashes;
  private Various.Bundle[] bundles;

  private static int binarySearch(int[] array, int value) {
    int lo = 0;
    int hi = array.length - 1;

    while (lo <= hi) {
      final int mid = (lo + hi) >>> 1;
      final int midVal = array[mid];

      if (midVal < value) {
        lo = mid + 1;
      } else if (midVal > value) {
        hi = mid - 1;
      } else {
        return mid;
      }
    }
    return ~lo;
  }

  private static void sort(Various.Bundle[] bundles) {
    for (int i = 0; i < bundles.length - 1; i++) {
      for (int j = i + 1; j > 0; j--) {
        Various.Bundle a = bundles[j - 1];
        Various.Bundle b = bundles[j];
        if (compareBundle(a, b) <= 0) break;
        exchange(bundles, j - 1, j);
      }
    }
  }

  private static int compareBundle(Various.Bundle a, Various.Bundle b) {
    int hashA = System.identityHashCode(a.itemType);
    int hashB = System.identityHashCode(b.itemType);
    return Integer.compare(hashA, hashB);
  }

  private static void exchange(Various.Bundle[] bundles, int i, int j) {
    Various.Bundle tmp = bundles[i];
    bundles[i] = bundles[j];
    bundles[j] = tmp;
  }

  @Override public void init(List<Various.Bundle> bundleList) {
    initBundles(bundleList);
    initHashes();
  }

  private void initBundles(List<Various.Bundle> bundleList) {
    bundles = bundleList.toArray(new Various.Bundle[bundleList.size()]);
    sort(bundles);
  }

  private void initHashes() {
    hashes = new int[bundles.length];
    for (int i = 0; i < hashes.length; i++) {
      hashes[i] = System.identityHashCode(bundles[i].itemType);
    }
  }

  @Override public int viewTypeOf(Class itemType) {
    int hash = System.identityHashCode(itemType);
    int index = binarySearch(hashes, hash);

    if (index < 0) {
      throw new ItemNotFoundException(itemType);
    }

    if (itemType.equals(bundles[index].itemType)) return index;

    for (int i = index + 1; i < bundles.length && hash == hashes[i]; i++) {
      if (itemType.equals(bundles[index].itemType)) return i;
    }

    for (int i = index - 1; i >= 0 && hash == hashes[i]; i--) {
      if (itemType.equals(bundles[index].itemType)) return i;
    }

    throw new ItemNotFoundException(itemType);
  }

  @Override public Various.Bundle bundleOf(int viewType) {
    return bundles[viewType];
  }
}
