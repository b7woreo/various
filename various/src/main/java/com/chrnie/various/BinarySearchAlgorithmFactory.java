package com.chrnie.various;

import java.util.List;

public class BinarySearchAlgorithmFactory extends Algorithm.Factory {
  @Override Algorithm create(List<Item> itemList) {
    return new BinarySearchAlgorithm(itemList);
  }
}
