package com.chrnie.various;

public class ItemNotFoundException extends RuntimeException {
  public ItemNotFoundException(Class dateType) {
    super(String.format("%s not found match item, make sure it has been registered",
        dateType.getName()));
  }
}
