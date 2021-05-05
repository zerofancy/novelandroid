package top.ntutn.readview

data class LineModel(
    val stringList: List<String>,
    val strX: List<Int>,
    val strDiff: Int,
    val strColors: List<Int>
)

data class PageModel(val lineModels: List<LineModel>)

data class ChapterModel(var pageModels: List<PageModel>, var index: Int)