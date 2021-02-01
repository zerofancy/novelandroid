package top.ntutn.novelrecommend.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import top.ntutn.novelrecommend.databinding.DialogConfigEditBinding

class ConfigEditDialogFragment : DialogFragment() {
    private lateinit var binding: DialogConfigEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogConfigEditBinding.inflate(inflater, container, false)
        return binding.root
    }
}