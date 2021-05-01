package top.ntutn.novelrecommend.ui.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import top.ntutn.novelrecommend.data.LoginRepository
import top.ntutn.novelrecommend.data.model.LoggedInUser

class MeViewModel : ViewModel() {
    private val _currentUser = MutableLiveData<LoggedInUser>().apply { value = null }

    val currentUser: LiveData<LoggedInUser> = _currentUser
    val isLoggedIn: Boolean
        get() = _currentUser.value != null

    fun refreshUserInfo() {
        if (_currentUser.value != LoginRepository.user) {
            _currentUser.value = LoginRepository.user
        }
    }
}