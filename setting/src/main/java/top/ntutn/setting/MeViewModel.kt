package top.ntutn.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.ntutn.login.LoggedInUser
import top.ntutn.login.LoginServiceDelegate

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