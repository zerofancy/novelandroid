package top.ntutn.novelrecommend.model

data class NovelModel(
    val id: Long? = null,
    val title: String? = "",
    val author: String? = "佚名",
    val description: String? = "",
    val tags: Set<String>? = hashSetOf(),
    val cover: String? = null,
    val chapterCount: Int = 0,
    val path: String = "",
    val localId: Long? = 0L, // 防止抓到重复视频
    var isLiked: Boolean = false,
    var isStared: Boolean = false
)