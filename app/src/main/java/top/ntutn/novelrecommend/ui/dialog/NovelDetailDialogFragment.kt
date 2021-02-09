package top.ntutn.novelrecommend.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import top.ntutn.novelrecommend.databinding.DialogNovelDetailBinding
import top.ntutn.novelrecommend.ui.fragment.DiscoverViewModel

class NovelDetailDialogFragment : DialogFragment() {
    private lateinit var binding: DialogNovelDetailBinding
    private val discoverViewModel by activityViewModels<DiscoverViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogNovelDetailBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        val currentPosition = discoverViewModel.currentPosition.value ?: 0
        val currentNovel = discoverViewModel.novelList.value?.get(currentPosition)
        if (currentNovel != null) {
            binding.apply {
                titleTextView.text = currentNovel.title
                authorTextView.text = currentNovel.author
                tagsTextView.text = currentNovel.tags.toString()
            }
        } else {
            dismiss()
        }
    }

    companion object {
        fun newInstance(): NovelDetailDialogFragment =
            NovelDetailDialogFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}