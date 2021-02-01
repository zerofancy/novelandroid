package top.ntutn.novelrecommend.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import top.ntutn.novelrecommend.databinding.DialogConfigEditBinding

class ConfigEditDialogFragment : DialogFragment() {
    private lateinit var binding: DialogConfigEditBinding
    private val configEditViewModel by viewModels<ConfigEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogConfigEditBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        configEditViewModel.key.observe(this) {
            binding.title.text = it
        }
        configEditViewModel.value.observe(this) {
            binding.valueEditText.setText(it)
        }
    }

    private fun initData() {
        arguments?.let { bundle ->
            val key = bundle.getString(BUNDLE_KEY) ?: return
            val value = bundle.getString(BUNDLE_VALUE) ?: return
            configEditViewModel.setData(key, value)
        }
    }

    companion object {
        private const val BUNDLE_KEY = "key"
        private const val BUNDLE_VALUE = "value"

        fun newInstance(key: String, value: String): ConfigEditDialogFragment =
            ConfigEditDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_KEY, key)
                    putString(BUNDLE_VALUE, value)
                }
            }
    }
}