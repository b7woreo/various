# various

这是一个用来创建RecyclerView.Adapter的工具类，它着重要于完成数据到ViewHolder的映射，以便捷的方式创建多类型Item Adapter、复用Item逻辑，可以避免编写大量的Adapter模板代码。在使用了Java8 lambda表达式之后，这也许是最方便的、简洁的Adapter创建方式。

## 用法
### 1. 添加依赖
```
implementation 'com.chrnie:various:0.3.2'
```

### 2. 编写数据类型和ViewHolder
每个Item都有一个对应的数据类型，例如一条新闻：
``` java
public class News {
    public final String title;
    public final String brief;
}
```
每种数据类型至少有一个相对应的ViewHolder，ViewHolder提供一个静态方法完成创建和一个非静态方法完成数据绑定。
``` java
public class NewsViewHolder extends ViewHolder {

    public static NewsViewHolder create(LayoutInflater inflater, ViewGroup container) {
        View itemView = inflater.inflater(..., container, false);
        return new NewsViewHolder(itemView);
    }

    private NewsViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(News item, List<Object> payloads) {
        // bind data to view
    }
}
```
这样Item中的逻辑全部转移到了ViewHolder中，可以在多个列表间轻松复用。

### 3. 创建Adapter
注册Item的相关参数（数据类型，创建方法，数据绑定方法）。在使用了lambda表达式之后，你将看到创建一个Adapter是如此简洁。之后你只需关注dataList中的数据，Adapter帮你完成对应数据类型到ViewHolder的映射。
``` java
Adapter adapter = Various.of(dataList)
        .register(News.class, NewsViewHolder::create, NewsViewHolder::bind)
        .build();
```

## 进阶用法
### 带回调的ViewHolder
有时你需要使用到 Adapter 中关于 ViewHolder 的回调，Various 为你提供了 ViewHolderCallback，你只需实现 ViewHolderCallback 接口并重写相应的回调方法即可。
``` java
public class CallbackViewHolder extends ViewHolder implements ViewHolderCallback {

    public CallbackViewHolder(View itemView) {
        super(itemView);
    }

    @Override 
    public boolean onFailedToRecycleView() {
        // do something on failed to recycle view and return value
        super.onFailedToRecycleView();
    }

    @Override 
    public void onViewAttachedToWindow() {
        // do something on view attached to window
    }

    @Override 
    public void onViewDetachedFromWindow() {
        // do something on view detached from window
    }

    @Override 
    public void onViewRecycled() {
        // do something on view recycled
    }
}
```

### 工厂方法创建 ViewHolder
如果不喜欢 lambda 的方式创建 ViewHolder，那么还可以选择使用工厂方法创建 ViewHolder。
``` java
public class MyViewHolderFactory implements Factory<Object, MyViewHolder> {

  private static final MyViewHolderFactory INSTANCE = new MyViewHolderFactory();

  public static MyViewHolderFactory get() {
    return INSTANCE;
  }

  @Override
  public MyViewHolder create(LayoutInflater inflater, ViewGroup container) {
    View itemView = inflater.inflater(..., container, false);
    return new MyViewHolder(itemView);
  }

  public class MyViewHolder extends ViewHolder<Object> {

    MyViewHolder(View itemView) {
      super(itemView);
    }

    @Override
    protected void bind(Object date, List<Object> payloads) {

    }
  }
}
```

### 定制 ItemMatcher
如果对默认的 ItemMatcher 不满意，那么可以实现 ItemMatcher、ItemMatcher.Factory 接口并在使用 Various 创建 Adapter 时将其作为参数传入。
``` java
Various.of(dataList, new CustomItemMatcherFacoty())
```

## License
MIT License