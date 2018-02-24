package com.chrnie.various;

import android.support.annotation.NonNull;
import java.util.List;

public interface ItemMatcher {

  int getViewType(@NonNull Object date);

  @NonNull
  Item getItem(int viewType);

  interface Factory {
    @NonNull
    ItemMatcher create(@NonNull List<Item> itemList);
  }
}
