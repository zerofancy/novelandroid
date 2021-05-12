package top.ntutn.commonui.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 解决viewBinding在Fragment中的内存泄漏问题
 * 来自https://jamie.sanson.dev/blog/handling-lifecycle-with-view-binding-in-fragments/
 */
fun <T> Fragment.viewLifecycle(): ReadWriteProperty<Fragment, T> =
    object: ReadWriteProperty<Fragment, T>, LifecycleObserver {

        // A backing property to hold our value
        private var binding: T? = null

        // The current LifecycleOwner
        private var viewLifecycleOwner: LifecycleOwner? = null

        init {
            // Observe the View LifecycleOwner, then observe the new lifecycle
            this@viewLifecycle
                .viewLifecycleOwnerLiveData
                .observe(this@viewLifecycle, Observer { newLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.removeObserver(this)
                    viewLifecycleOwner = newLifecycleOwner.also {
                        it.lifecycle.addObserver(this)
                    }
                })
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            // Clear out backing property just before onDestroyView
            binding = null
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): T {
            // Return the backing property if it's set
            return this.binding!!
        }
        override fun setValue(
            thisRef: Fragment,
            property: KProperty<*>,
            value: T
        ) {
            // Set the backing property
            this.binding = value
        }
    }