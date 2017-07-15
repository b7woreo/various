package com.chrnie.various;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class) public class AdapterTest {

  private List<Object> itemList = new ArrayList<>();
  private Adapter<ViewHolder> adapter;

  @Before public void setUp() {
    adapter = Various.of(itemList)
        .register(Integer.class, IntViewHolder::create)
        .register(String.class, StringViewHolder::create, StringViewHolder::bind)
        .register(Double.class, DoubleViewHolder::create, DoubleViewHolder::bind,
            DoubleViewHolder::bindWithPayload)
        .build();

    itemList.addAll(Arrays.asList(1, "Hello", 2.0));
  }

  @Test public void getItemCount() {
    assertEquals(itemList.size(), adapter.getItemCount());
  }

  @Test public void viewType() {
    ViewGroup container = new RecyclerView(InstrumentationRegistry.getContext());
    int intViewType = adapter.getItemViewType(0);
    ViewHolder intViewHolder = adapter.createViewHolder(container, intViewType);
    assertEquals(IntViewHolder.class, intViewHolder.getClass());

    int stringViewType = adapter.getItemViewType(1);
    ViewHolder stringViewHolder = adapter.createViewHolder(container, stringViewType);
    assertEquals(StringViewHolder.class, stringViewHolder.getClass());
  }

  @Test(expected = BindException.class) public void bind() {
    ViewGroup container = new RecyclerView(InstrumentationRegistry.getContext());
    int viewType = adapter.getItemViewType(1);
    ViewHolder viewHolder = adapter.createViewHolder(container, viewType);
    adapter.onBindViewHolder(viewHolder, 1);
  }

  @Test public void bindWithPayload() {
    ViewGroup container = new RecyclerView(InstrumentationRegistry.getContext());
    int viewType = adapter.getItemViewType(2);
    ViewHolder viewHolder = adapter.createViewHolder(container, viewType);
    try {
      adapter.onBindViewHolder(viewHolder, 2);
      throw new FailException();
    } catch (Exception e) {
      assertEquals(BindException.class, e.getClass());
    }

    try {
      adapter.onBindViewHolder(viewHolder, 2, Arrays.asList(new Object()));
      throw new FailException();
    } catch (Exception e) {
      assertEquals(BindWithPayloadException.class, e.getClass());
    }
  }

  private static class IntViewHolder extends ViewHolder {

    private IntViewHolder(View itemView) {
      super(itemView);
    }

    public static IntViewHolder create(LayoutInflater inflater, ViewGroup container) {
      View itemView = new View(container.getContext());
      return new IntViewHolder(itemView);
    }
  }

  private static class StringViewHolder extends ViewHolder {

    private StringViewHolder(View itemView) {
      super(itemView);
    }

    public static StringViewHolder create(LayoutInflater inflater, ViewGroup container) {
      View itemView = new View(container.getContext());
      return new StringViewHolder(itemView);
    }

    public void bind(String item) {
      throw new BindException();
    }
  }

  private static class DoubleViewHolder extends ViewHolder {

    public static DoubleViewHolder create(LayoutInflater inflater, ViewGroup container) {
      View itemView = new View(container.getContext());
      return new DoubleViewHolder(itemView);
    }

    private DoubleViewHolder(View itemView) {
      super(itemView);
    }

    public void bind(Double item) {
      throw new BindException();
    }

    public void bindWithPayload(Double item, List<Object> payload) {
      throw new BindWithPayloadException();
    }
  }

  public static class BindException extends RuntimeException {

  }

  public static class BindWithPayloadException extends RuntimeException {

  }

  public static class FailException extends RuntimeException {

  }
}
