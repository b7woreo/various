package com.chrnie.various;

import java.util.List;

final class BinarySearchAlgorithm extends Algorithm {

  private int[] hashes;
  private Item[] items;

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

  private static void sort(Item[] bundles) {
    for (int i = 0; i < bundles.length - 1; i++) {
      for (int j = i + 1; j > 0; j--) {
        Item a = bundles[j - 1];
        Item b = bundles[j];
        if (compareBundle(a, b) <= 0) break;
        exchange(bundles, j - 1, j);
      }
    }
  }

  private static int compareBundle(Item a, Item b) {
    int hashA = System.identityHashCode(a.itemType);
    int hashB = System.identityHashCode(b.itemType);
    return Integer.compare(hashA, hashB);
  }

  private static void exchange(Item[] bundles, int i, int j) {
    Item tmp = bundles[i];
    bundles[i] = bundles[j];
    bundles[j] = tmp;
  }

  BinarySearchAlgorithm(List<Item> bundleList) {
    initBundlesAndCacheHashes(bundleList);
  }

  private void initBundlesAndCacheHashes(List<Item> bundleList) {
    items = bundleList.toArray(new Item[bundleList.size()]);
    sort(items);

    hashes = new int[items.length];
    for (int i = 0; i < hashes.length; i++) {
      hashes[i] = System.identityHashCode(items[i].itemType);
    }
  }

  @Override public int viewTypeOf(Class itemType) {
    int hash = System.identityHashCode(itemType);
    int index = binarySearch(hashes, hash);

    if (index < 0) {
      throw new ItemNotFoundException(itemType);
    }

    if (itemType.equals(items[index].itemType)) return index;

    for (int i = index + 1; i < items.length && hash == hashes[i]; i++) {
      if (itemType.equals(items[index].itemType)) return i;
    }

    for (int i = index - 1; i >= 0 && hash == hashes[i]; i--) {
      if (itemType.equals(items[index].itemType)) return i;
    }

    throw new ItemNotFoundException(itemType);
  }

  @Override public Item bundleOf(int viewType) {
    return items[viewType];
  }
}
