package top.ntutn.novelrecommend.model

data class MongoNovelBean(
    val id: Int,
    val title: String, // 小说名（不用name是怕跟啥东西撞车）
    val author: String, // 小说作者
    val cover: String,// 小说封面
    val tags: List<String>,// 关键词
    val description: String,//描述
    val chapterCount: Int,//章节数
    val path: String//路径
)