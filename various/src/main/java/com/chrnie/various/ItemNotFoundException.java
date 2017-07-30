package com.chrnie.various;

public class ItemNotFoundException extends RuntimeException {
  public ItemNotFoundException(Class itemType) {
    super(String.format("%s not found match item, make sure it has been registered",
        itemType.getName()));
  }
}
