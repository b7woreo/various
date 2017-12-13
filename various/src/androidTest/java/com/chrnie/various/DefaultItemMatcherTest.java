package com.chrnie.various;

import android.support.test.runner.AndroidJUnit4;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DefaultItemMatcherTest {

  private ItemMatcher itemMatcher1;
  private ItemMatcher itemMatcher2;
  private Item integerItem = new Item(Integer.class, (inflater, container) -> null, (viewHolder, date, payloads) -> {
  });

  @Before
  public void setup() {
    DefaultItemMatcherFactory factory = DefaultItemMatcherFactory.getInstance();
    itemMatcher1 = factory.create(Arrays.asList(
        new Item(Integer.class, (inflater, container) -> null, (viewHolder, date, payloads) -> {
        }),
        new Item(String.class, (inflater, container) -> null, (viewHolder, date, payloads) -> {
        })
    ));

    itemMatcher2 = factory.create(Arrays.asList(integerItem));
  }

  @Test
  public void viewTypeCorrect() {
    // difference ItemMatcher has same view type for same date type
    int integerViewType1 = itemMatcher1.getViewType(0);
    int integerViewType2 = itemMatcher2.getViewType(0);
    Assert.assertTrue(integerViewType1 == integerViewType2);

    // difference instance of data type has same view type
    int integerViewType3 = itemMatcher1.getViewType(1);
    Assert.assertTrue(integerViewType1 == integerViewType3);

    // difference data type has difference view type
    int stringViewType = itemMatcher1.getViewType("");
    Assert.assertTrue(integerViewType1 != stringViewType);
  }

  @Test
  public void itemCorrect() {
    int viewType = itemMatcher2.getViewType(0);
    Item item = itemMatcher2.getItem(viewType);
    Assert.assertEquals(integerItem, item);
  }
}
