package top.ntutn.novelrecommend.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import top.ntutn.novelrecommend.databinding.DialogNovelDetailBinding
import top.ntutn.novelrecommend.ui.viewmodel.BookShelfViewModel
import top.ntutn.novelrecommend.ui.viewmodel.DiscoverViewModel
import top.ntutn.novelrecommend.utils.showSnackBar

class NovelDetailDialogFragment : DialogFragment() {
    private lateinit var binding: DialogNovelDetailBinding
    private val discoverViewModel by activityViewModels<DiscoverViewModel>()
    private val bookShelfViewModel by activityViewModels<BookShelfViewModel>()

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
        if (currentNovel == null) {
            dismiss()
            return
        }

        binding.apply {
            likeButton.setOnClickListener { likeButton.toggle() } // TODO 点赞
            starButton.setOnClickListener {
                starButton.toggle()
                if (starButton.isChecked) {
                    bookShelfViewModel.addBook(currentNovel)
                } else {
                    bookShelfViewModel.removeBook(currentNovel)
                }
            }
            shareButton.setOnClickListener { shareButton.showSnackBar("分享功能暂时不可用") }
        }
        binding.apply {
            titleTextView.text = currentNovel.title
            authorTextView.text = currentNovel.author
            tagsTextView.text = currentNovel.tags.toString()
        }
        binding.starButton.setCheckedWithoutAnimator((bookShelfViewModel.books.value?.find { it.id == currentNovel.id }) != null)
    }

    companion object {
        fun newInstance(): NovelDetailDialogFragment =
            NovelDetailDialogFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}