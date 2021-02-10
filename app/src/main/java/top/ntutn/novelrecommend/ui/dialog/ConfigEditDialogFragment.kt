package top.ntutn.novelrecommend.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import top.ntutn.novelrecommend.databinding.DialogConfigEditBinding
import top.ntutn.novelrecommend.ui.viewmodel.ConfigEditViewModel
import top.ntutn.novelrecommend.utils.EventBusWrapper

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
        binding.cancelButton.setOnClickListener {
            EventBusWrapper.post(
                ConfigEditDialogCloseEvent(
                    false,
                    configEditViewModel.key.value,
                    configEditViewModel.value.value
                )
            )
            dismiss()
        }
        binding.saveButton.setOnClickListener {
            EventBusWrapper.post(
                ConfigEditDialogCloseEvent(
                    true,
                    configEditViewModel.key.value,
                    configEditViewModel.value.value
                )
            )
            dismiss()
        }

        configEditViewModel.key.observe(this) {
            binding.title.text = it
        }
        configEditViewModel.value.observe(this) {
            if (binding.valueEditText.text.toString() != it) binding.valueEditText.setText(it)
        }
        binding.valueEditText.doOnTextChanged { text, _, _, _ ->
            configEditViewModel.editValue(text.toString())
        }
    }

    private fun initData() {
        arguments?.let { bundle ->
            val key = bundle.getString(BUNDLE_KEY) ?: throw IllegalArgumentException()
            configEditViewModel.setData(key)
        }
    }

    companion object {
        private const val BUNDLE_KEY = "key"
        private const val BUNDLE_VALUE = "value"

        fun newInstance(key: String): ConfigEditDialogFragment =
            ConfigEditDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_KEY, key)
                }
            }
    }
}

data class ConfigEditDialogCloseEvent(val saveResult: Boolean, val key: String?, val value: String?)
