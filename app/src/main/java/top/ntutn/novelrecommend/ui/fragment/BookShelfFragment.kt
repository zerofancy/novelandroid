package top.ntutn.novelrecommend.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import top.ntutn.novelrecommend.databinding.FragmentBookshelfBinding
import top.ntutn.novelrecommend.ui.base.BaseFragment
import top.ntutn.novelrecommend.ui.viewmodel.BookShelfViewModel

class BookShelfFragment : BaseFragment() {
    private val bookShelfViewModel by activityViewModels<BookShelfViewModel>()
    private lateinit var binding: FragmentBookshelfBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookshelfBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {

    }
}