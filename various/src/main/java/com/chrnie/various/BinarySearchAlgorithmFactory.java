package com.chrnie.various;

import java.util.List;

public class BinarySearchAlgorithmFactory extends Algorithm.Factory {
  @Override Algorithm create(List<Various.Bundle> bundleList) {
    return new BinarySearchAlgorithm(bundleList);
  }
}
