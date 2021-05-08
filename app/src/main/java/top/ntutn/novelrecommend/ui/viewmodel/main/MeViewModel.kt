package top.ntutn.novelrecommend.ui.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.ntutn.login.LoggedInUser
import top.ntutn.login.LoginRepository
import top.ntutn.login.LoginServiceDelegate
import top.ntutn.login.Result

class MeViewModel : ViewModel() {
    private val _currentUser = MutableLiveData<LoggedInUser>().apply { value = null }

    val currentUser: LiveData<LoggedInUser> = _currentUser
    val isLoggedIn: Boolean
        get() = _currentUser.value != null

    fun refreshUserInfo() {
        LoginServiceDelegate.refresh {
            _currentUser.value = it
        }
    }

    fun onLoginSuccess(user: LoggedInUser) {
        _currentUser.value = user
    }
}