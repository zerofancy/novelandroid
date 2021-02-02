package top.ntutn.novelrecommend.ui.dialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import top.ntutn.novelrecommend.databinding.DialogConfigEditBinding
import kotlin.properties.Delegates

class ConfigEditDialogFragment : DialogFragment() {
    private lateinit var binding: DialogConfigEditBinding
    private val configEditViewModel by viewModels<ConfigEditViewModel>()
    private var requestCode by Delegates.notNull<Int>()

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
            targetFragment?.onActivityResult(requestCode, Activity.RESULT_CANCELED, null)
            dismiss()
        }
        binding.saveButton.setOnClickListener {
            targetFragment?.onActivityResult(requestCode, Activity.RESULT_OK, Intent().apply {
                putExtra(BUNDLE_KEY, configEditViewModel.key.value)
                putExtra(BUNDLE_VALUE, configEditViewModel.value.value)
            })
            dismiss()
        }

        configEditViewModel.key.observe(this) {
            binding.title.text = it
        }
        configEditViewModel.value.observe(this) {
            binding.valueEditText.setText(it)
        }
    }

    private fun initData() {
        arguments?.let { bundle ->
            val key = bundle.getString(BUNDLE_KEY) ?: throw IllegalArgumentException()
            requestCode = bundle.getInt(BUNDLE_VALUE)
            configEditViewModel.setData(key)
        }
    }

    companion object {
        private const val BUNDLE_KEY = "key"
        private const val BUNDLE_VALUE = "value"
        private const val BUNDLE_REQUEST_CODE = "request_code"

        fun newInstance(key: String, requestCode: Int): ConfigEditDialogFragment =
            ConfigEditDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_KEY, key)
                    putInt(BUNDLE_REQUEST_CODE, requestCode)
                }
            }
    }
}