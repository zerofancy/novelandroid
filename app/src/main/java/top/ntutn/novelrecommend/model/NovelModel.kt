package top.ntutn.novelrecommend.model;

data class NovelModel(
    val id: Long? = null,
    val title: String? = "",
    val author: String? = "佚名",
    val content: String? = "",
    val tags: Set<String>? = hashSetOf()
)