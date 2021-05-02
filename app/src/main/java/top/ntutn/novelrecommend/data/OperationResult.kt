package top.ntutn.novelrecommend.data

data class OperationResult<out T>(
    val code: Int,
    val message: String? = null,
    val obj: T? = null
)
