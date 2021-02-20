package top.ntutn.novelrecommend.arch

import androidx.lifecycle.LiveData

/**
 * 解决Livedata的空安全问题
 *
 */
abstract class CheckedLiveData<T> : LiveData<T>() {
    override fun getValue(): T {
        return super.getValue() ?: run {
            val res = initValue()
            value = res
            initValue()
        }
    }

    protected abstract fun initValue(): T
}

class InitedLiveData<E>(private val initBlock: () -> E) : CheckedLiveData<E>() {
    override fun initValue() = initBlock.invoke()

    public override fun setValue(value: E) {
        super.setValue(value)
    }

    public override fun postValue(value: E) {
        super.postValue(value)
    }
}