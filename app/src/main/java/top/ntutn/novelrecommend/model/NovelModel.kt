package top.ntutn.novelrecommend.model

data class NovelModel(
    val id: Long? = null,
    val title: String? = "",
    val author: String? = "佚名",
    val content: String? = "",
    val tags: Set<String>? = hashSetOf(),
    val cover: String? = null,
    val localId: Long? = 0L, // 防止抓到重复视频
    val isLiked: Boolean = false,
    val isStared: Boolean = false
)