package com.chrnie.various;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AdapterImplTest {

  private List<?> itemList;
  private Adapter adapter;

  @Before
  public void setup() {
    itemList = Arrays.asList(new Object());
    adapter = Various.of(itemList)
        .register(Object.class, (inflater, container) -> TestViewHolder.create(), TestViewHolder::bind)
        .build();
  }

  @Test
  public void viewHolderCreateAndBind() {
    Assert.assertEquals(itemList.size(), adapter.getItemCount());

    int viewType = adapter.getItemViewType(0);
    RecyclerView.ViewHolder viewHolder =
        adapter.createViewHolder(new RecyclerView(InstrumentationRegistry.getContext()), viewType);

    Assert.assertEquals(true, viewHolder instanceof TestViewHolder);
    Assert.assertEquals(TestViewHolder.INSTANCE, viewHolder);

    adapter.onBindViewHolder(viewHolder, 0);
    Assert.assertEquals(1, ((TestViewHolder) viewHolder).bindCount);

    adapter.onBindViewHolder(viewHolder, 0, Collections.emptyList());
    Assert.assertEquals(2, ((TestViewHolder) viewHolder).bindCount);
  }

  @Test
  public void viewHolderCallback() {
    TestViewHolder viewHolder = TestViewHolder.create();
    adapter.onFailedToRecycleView(viewHolder);
    Assert.assertEquals(true, viewHolder.onFailedToRecycleView);

    adapter.onViewAttachedToWindow(viewHolder);
    Assert.assertEquals(true, viewHolder.onViewAttachedToWindow);

    adapter.onViewDetachedFromWindow(viewHolder);
    Assert.assertEquals(true, viewHolder.onViewDetachedFromWindow);

    adapter.onViewRecycled(viewHolder);
    Assert.assertEquals(true, viewHolder.onViewRecycled);
  }

  public static class TestViewHolder extends Various.ViewHolder {

    boolean onFailedToRecycleView = false;
    boolean onViewAttachedToWindow = false;
    boolean onViewDetachedFromWindow = false;
    boolean onViewRecycled = false;
    int bindCount = 0;

    TestViewHolder(View itemView) {
      super(itemView);
    }

    @Override
    protected void bind(Object date, List payloads) {
      bindCount += 1;
    }

    @Override
    protected boolean onFailedToRecycleView() {
      onFailedToRecycleView = true;
      return super.onFailedToRecycleView();
    }

    @Override
    protected void onViewAttachedToWindow() {
      onViewAttachedToWindow = true;
      super.onViewAttachedToWindow();
    }

    @Override
    protected void onViewDetachedFromWindow() {
      onViewDetachedFromWindow = true;
      super.onViewDetachedFromWindow();
    }

    @Override
    protected void onViewRecycled() {
      onViewRecycled = true;
      super.onViewRecycled();
    }

    static TestViewHolder INSTANCE;

    public static TestViewHolder create() {
      if (INSTANCE == null) {
        View itemView = new View(InstrumentationRegistry.getContext());
        INSTANCE = new TestViewHolder(itemView);
      }
      return INSTANCE;
    }
  }
}
