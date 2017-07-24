package com.chrnie.various;

import java.util.List;

public interface ItemPool {

  void init(List<Various.Bundle> bundleList);

  int viewTypeOf(Class itemType);

  Various.Bundle bundleOf(int viewType);
}
