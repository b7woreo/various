package com.chrnie.various;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class) public class LifecycleTest {

  private List<Object> itemList = new ArrayList<>();
  private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

  @Before public void setUp() {
    adapter = Various.of(itemList).register(Integer.class, TestViewHolder::create).build();
    itemList.add(1);
  }

  @Test public void onFailedToRecycleView() {
    RecyclerView recyclerView = new RecyclerView(InstrumentationRegistry.getContext());
    int viewType = adapter.getItemViewType(0);
    TestViewHolder viewHolder = (TestViewHolder) adapter.onCreateViewHolder(recyclerView, viewType);
    assertEquals(viewHolder.onFailedToRecycleView(), adapter.onFailedToRecycleView(viewHolder));
  }

  @Test(expected = ExpectException.class) public void onViewAttachedToWindow() {
    RecyclerView recyclerView = new RecyclerView(InstrumentationRegistry.getContext());
    int viewType = adapter.getItemViewType(0);
    TestViewHolder viewHolder = (TestViewHolder) adapter.onCreateViewHolder(recyclerView, viewType);
    adapter.onViewAttachedToWindow(viewHolder);
  }

  @Test(expected = ExpectException.class) public void onViewDetachedFromWindow() {
    RecyclerView recyclerView = new RecyclerView(InstrumentationRegistry.getContext());
    int viewType = adapter.getItemViewType(0);
    TestViewHolder viewHolder = (TestViewHolder) adapter.onCreateViewHolder(recyclerView, viewType);
    adapter.onViewDetachedFromWindow(viewHolder);
  }

  @Test(expected = ExpectException.class) public void onViewRecycled() {
    RecyclerView recyclerView = new RecyclerView(InstrumentationRegistry.getContext());
    int viewType = adapter.getItemViewType(0);
    TestViewHolder viewHolder = (TestViewHolder) adapter.onCreateViewHolder(recyclerView, viewType);
    adapter.onViewRecycled(viewHolder);
  }

  private static class TestViewHolder extends Various.ViewHolder {

    private TestViewHolder(View itemView) {
      super(itemView);
    }

    public static TestViewHolder create(LayoutInflater inflater, ViewGroup container) {
      View itemView = new View(container.getContext());
      return new TestViewHolder(itemView);
    }

    @Override public boolean onFailedToRecycleView() {
      return true;
    }

    @Override public void onViewAttachedToWindow() {
      throw new ExpectException();
    }

    @Override public void onViewDetachedFromWindow() {
      throw new ExpectException();
    }

    @Override public void onViewRecycled() {
      throw new ExpectException();
    }
  }

  private static class ExpectException extends RuntimeException {

  }
}
