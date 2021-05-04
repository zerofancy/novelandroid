package top.ntutn.commonutil

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val id: Long? = null,
    val username: String,
    val type: Int,
    var description: String? = null,
    var avatar: String? = null,
    var nickname: String? = null
)