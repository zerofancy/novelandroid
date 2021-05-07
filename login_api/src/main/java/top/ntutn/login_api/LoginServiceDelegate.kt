package top.ntutn.login_api

object LoginServiceDelegate : LoginService by LoginService.getInstance()!!