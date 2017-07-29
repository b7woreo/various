package com.chrnie.various;

import java.util.ArrayList;
import java.util.List;

public final class DefaultAlgorithmFactory extends Algorithm.Factory {

  @Override Algorithm create(List<Various.Bundle> bundleList) {
    return new DefaultAlgorithm(bundleList);
  }
}
