package top.ntutn.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val eulaError: Int? = null,
    val isDataValid: Boolean = false
)