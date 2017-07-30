# various

这是一个用来创建RecyclerView.Adapter的工具类，它着重要于完成数据到ViewHolder的映射，以便捷的方式创建多类型Item Adapter、复用Item逻辑，可以避免编写大量的Adapter模板代码。在使用了Java8 lambda表达式之后，这也许是最方便的、简洁的Adapter创建方式。

## 用法
### 1. 添加依赖
```
compile 'com.chrnie:various:0.2.1'
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

    public void bind(News item) {
        // bind data to view
    }
}
```
这样Item中的逻辑全部转移到了ViewHolder中，可以在多个列表间轻松复用。

### 3. 创建Adapter
注册Item的相关参数（数据类型，创建方法，数据绑定方法）。在使用了lambda表达式之后，你将看到创建一个Adapter是如此简洁。之后你只需关注itemList中的数据，Adapter帮你完成对应数据类型到ViewHolder的映射。
``` java
Adapter adapter = Various.of(dataList)
        .register(News.class, NewsViewHolder::create, NewsViewHolder::bind)
        .build();
```

## 进阶用法
### 1. 带生命周期的ViewHolder
有时你需要使用到ViewHolder的生命周期，Various为你提供了LifecycleViewHolder，你只需继承并重写相应的生命周期方法即可。
``` java
public class MyLifecycleViewHolder extends Various.LifecycleViewHolder {

    public static MyLifecycleViewHolder create(LayoutInflater inflater, ViewGroup container) {
        View itemView = inflater.inflater(..., container, false);
        return new MyLifecycleViewHolder(itemView);
    }

    private MyLifecycleViewHolder(View itemView) {
        super(itemView);
    }

    @Override public boolean onFailedToRecycleView() {
        // do something on failed to recycle view and return value
        return false;
    }

    @Override public void onViewAttachedToWindow() {
        // do something on view attached to window
    }

    @Override public void onViewDetachedFromWindow() {
        // do something on view detached from window
    }

    @Override public void onViewRecycled() {
        // do something on view recycled
    }
}
```

### 2. 复杂类型列表性能问题
进过测试，在一般情况下（一个列表有几十种类型Item）默认的算法不会存在性能瓶颈。在极端情况下（一个列表有成百上千种类型的Item）给出了一个基于二分查找的算法，可以提升性能（在类型数量巨大时才会有明显提升，正常不推荐使用）。
``` java
Various.of(dataList, new BinarySearchItemPool());
```

## License
MIT License