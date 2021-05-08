package top.ntutn.login

object LoginServiceDelegate : LoginService by LoginService.getInstance()!!