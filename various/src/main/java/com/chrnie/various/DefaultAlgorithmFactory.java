package com.chrnie.various;

import java.util.List;

public final class DefaultAlgorithmFactory extends Algorithm.Factory {

  @Override Algorithm create(List<Item> itemList) {
    return new DefaultAlgorithm(itemList);
  }
}
