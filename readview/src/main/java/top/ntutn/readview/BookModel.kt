package top.ntutn.readview

@Deprecated("旧的小说阅读组件")
data class LineModel(
    val stringList: List<String>,
    val strX: List<Int>,
    val strDiff: Int,
    val strColors: List<Int>
)

@Deprecated("旧的小说阅读组件")
data class PageModel(val lineModels: List<LineModel>)

@Deprecated("已经不再使用的ReadView")
data class ChapterModel(var pageModels: List<PageModel>, var index: Int)