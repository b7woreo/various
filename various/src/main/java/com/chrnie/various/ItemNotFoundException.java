package com.chrnie.various;

public class ItemNotFoundException extends RuntimeException {
  public ItemNotFoundException(Class itemType) {
    super(itemType.getName() + "not found match ViewHolder, make sure it has been registered");
  }
}
