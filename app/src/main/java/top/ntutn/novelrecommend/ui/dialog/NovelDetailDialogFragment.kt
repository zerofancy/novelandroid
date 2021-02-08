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
        binding = DialogNovelDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

}