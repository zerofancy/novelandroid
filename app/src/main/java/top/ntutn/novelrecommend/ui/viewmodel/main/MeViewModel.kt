package top.ntutn.novelrecommend.ui.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.ntutn.novelrecommend.data.LoginRepository
import top.ntutn.novelrecommend.data.Result
import top.ntutn.commonutil.LoggedInUser

class MeViewModel : ViewModel() {
    private val _currentUser = MutableLiveData<LoggedInUser>().apply { value = null }

    val currentUser: LiveData<LoggedInUser> = _currentUser
    val isLoggedIn: Boolean
        get() = _currentUser.value != null

    fun refreshUserInfo() {
        viewModelScope.launch {
            _currentUser.value = withContext(Dispatchers.IO) {
                val result = LoginRepository.refresh()
                if (result is Result.Success) result.data else null
            }
        }
    }
}