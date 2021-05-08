package top.ntutn.login

data class OperationResult<out T>(
    val code: Int,
    val message: String? = null,
    val obj: T? = null
)
