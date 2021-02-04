package top.ntutn.novelrecommend.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import top.ntutn.novelrecommend.ui.fragment.DebugConfigFragment
import top.ntutn.novelrecommend.ui.fragment.DebugEntranceFragment

class DebugToolAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragmentClasses =
        listOf(DebugEntranceFragment::class.java, DebugConfigFragment::class.java)

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragmentClasses[position].newInstance()
    }
}