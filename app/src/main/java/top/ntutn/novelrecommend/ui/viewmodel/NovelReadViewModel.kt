package top.ntutn.novelrecommend.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import top.ntutn.commonui.common.InitedLiveData
import top.ntutn.commonutil.AppUtil
import top.ntutn.novelrecommend.R

class NovelReadViewModel : ViewModel() {
    private val _title = InitedLiveData {
        AppUtil.getApplicationContext().getString(R.string.title_activity_novel_read)
    }

    val title: LiveData<String> = _title

    /**
     * 获取小说信息
     */
    fun fetchNovelInfo(id: Long) {
        _title.value = id.toString() //TODO 请求小说信息
    }
}