package com.github.zerofancy.login

import android.content.Context
import com.github.zerofancy.login_api.LoggedInUser
import com.github.zerofancy.login_api.LoginService
import com.google.auto.service.AutoService

@AutoService(LoginService::class)
class LoginServiceImpl : LoginService {
    override fun getCurrentLoginUser(): LoggedInUser? {
        TODO("Not yet implemented")
    }

    override fun requireLoginUser(context: Context, block: (LoggedInUser?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}