# various

这是一个用来创建 RecyclerView.Adapter 的工具类，它着重要于完成数据到 ViewHolder 的映射，以便捷的方式创建多类型 Adapter、复用 Item 逻辑。

## 基础用法
### 1. 添加依赖

```
implementation 'com.chrnie:various:1.0.0'
```

### 2. 编写视图数据类型和 ViewBinder

每个Item都有一个对应的数据类型，例如一条新闻：

``` kotln
class NewsVO(
  val title: String,
  val brief: String
) {
  fun onClick() {
    // do something on click
  }
}
```

每种数据类型至少有一个相对应的 ViewBinder，ViewBinder API 接口类似于 Adapter。

``` kotlin
class NewsViewBinder() : ViewBinder<NewsVO, NewsViewBinder.NewsViewHolder>() {

  override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): NewsViewHolder {
    val view = inflater.inflate(R.layout.item_news, parent, false)
    return NewsViewHolder(view)
  }

  override fun onBindViewHolder(holder: NewsViewHolder, data: NewsVO, payloads: List<Any>) {
    holder.title.text = data.title
    holder.brief.text = data.brief
    holder.itemView.setOnClickListener { data.onClick() }
  }

  class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title: TextView by lazy { itemView.findViewById<TextView>(R.id.tvTitle) }
    val brief: TextView by lazy { itemView.findViewById<TextView>(R.id.tvBrief) }
  }
}
```

这样 Item 中的逻辑全部转移到了 ViewBinder 中，可以在多个列表间轻松复用。

### 3. 创建Adapter

``` kotlin
private val adapter: Various<NewsVO> by lazy {
    Various.Builder<NewsVO>()
      .register(NewsVO::class, NewsViewBinder())
      .build()
}
```

### 4. 添加要显示的数据

``` kotlin
adapter.dataList = arrayListOf(
    NewsVO("第一条新闻", "第一条新闻简介"),
    NewsVO("第二条新闻", "第二条新闻简介"),
    NewsVO("第三条新闻", "第三条新闻简介"),
    NewsVO("第四条新闻", "第四条新闻简介")
)
adapter.notifyDataSetChanged()
```

## 使用 Lambda

当一个 Item 不需要复用逻辑，并且只简单的创建和绑定视图，那么可以通过使用 Lambda 快速完成。

``` kotlin
private val adapter: Various<NewsVO> by lazy {
    Various.Builder<NewsVO>(CustomItemMatcherFactory())
      .register(
        NewsVO::class,
        { inflater, parent ->
          val view = inflater.inflate(R.layout.item_news, parent, false)
          NewsViewHolder(view)
        }, { holder, data, _ ->
          holder.title.text = data.title
          holder.brief.text = data.brief
          holder.itemView.setOnClickListener { data.onClick() }
        }
    ).build()
```

## 定制 ItemMatcher

如果对默认的 ItemMatcher 不满意，那么可以实现 ItemMatcher 及 ItemMatcher.Factory 接口并在创建 Various 时将其作为参数传入。

``` kotlin
private val adapter: Various<NewsVO> by lazy {
    Various.Builder<NewsVO>(CustomItemMatcherFactory())
        .register(NewsVO::class, NewsViewBinder())
        .build()
}
```

## License
MIT License