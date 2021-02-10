package top.ntutn.novelrecommend.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import top.ntutn.novelrecommend.databinding.DialogNovelDetailBinding
import top.ntutn.novelrecommend.ui.viewmodel.DiscoverViewModel
import top.ntutn.novelrecommend.utils.showSnackBar

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
        binding.apply {
            likeButton.setOnClickListener { likeButton.toggle() } // TODO 点赞
            starButton.setOnClickListener { starButton.toggle() }
            shareButton.setOnClickListener { shareButton.showSnackBar("分享功能暂时不可用") }
        }

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