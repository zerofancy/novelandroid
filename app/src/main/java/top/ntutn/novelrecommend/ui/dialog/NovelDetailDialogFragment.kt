package top.ntutn.novelrecommend.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import top.ntutn.commonutil.showSnackBar
import top.ntutn.novelrecommend.databinding.DialogNovelDetailBinding
import top.ntutn.novelrecommend.ui.viewmodel.main.BookShelfViewModel
import top.ntutn.novelrecommend.ui.viewmodel.main.DiscoverViewModel

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
        val currentPosition = discoverViewModel.currentPosition.value
        val currentNovel = discoverViewModel.novelList.value[currentPosition]

        binding.apply {
            likeButton.setOnClickListener {
                likeButton.toggle()
                discoverViewModel.bookLikedChange(likeButton.isChecked)
            }
            starButton.setOnClickListener {
                starButton.toggle()
                if (starButton.isChecked) {
                    bookShelfViewModel.addBook(currentNovel)
                } else {
                    bookShelfViewModel.removeBook(currentNovel)
                }
                discoverViewModel.bookStaredChange(starButton.isChecked)
            }
            shareButton.setOnClickListener { shareButton.showSnackBar("分享功能暂时不可用") }
        }
        binding.apply {
            titleTextView.text = currentNovel.title
            authorTextView.text = currentNovel.author
            tagsTextView.text = currentNovel.tags.toString()
        }
        binding.starButton.setCheckedWithoutAnimator((bookShelfViewModel.books.value.find { it.id == currentNovel.id }) != null)
        binding.likeButton.setCheckedWithoutAnimator(discoverViewModel.novelList.value[discoverViewModel.currentPosition.value].isLiked)
    }

    companion object {
        fun newInstance(): NovelDetailDialogFragment =
            NovelDetailDialogFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}