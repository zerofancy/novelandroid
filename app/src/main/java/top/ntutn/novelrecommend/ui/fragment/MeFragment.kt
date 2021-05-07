package top.ntutn.novelrecommend.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import top.ntutn.commonutil.showSnackBar
import top.ntutn.novelrecommend.BuildConfig
import top.ntutn.novelrecommend.R
import top.ntutn.novelrecommend.common.viewLifecycle
import top.ntutn.novelrecommend.databinding.FragmentMeBinding
import top.ntutn.novelrecommend.ui.activity.AboutActivity
import top.ntutn.novelrecommend.ui.activity.DebugHelperActivity
import top.ntutn.novelrecommend.ui.activity.SettingsActivity
import top.ntutn.commonui.base.BaseFragment
import top.ntutn.novelrecommend.ui.login.LoginActivity
import top.ntutn.novelrecommend.ui.viewmodel.main.MeViewModel

class MeFragment : BaseFragment() {
    companion object {
        private const val REQ_START_LOGIN = 0
    }

    private var binding by viewLifecycle<FragmentMeBinding>()
    private val meViewModel by viewModels<MeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeBinding.inflate(inflater)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.aboutContainer.setOnClickListener {
            AboutActivity.actionStart(requireContext())
        }

        binding.connectWithAuthorContainer.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, "ntutn.top@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "书虫软件使用咨询")
                putExtra(Intent.EXTRA_TEXT, "你好，我是《书虫》软件的用户，我有一些问题需要咨询……")
            }

            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                it.showSnackBar("未找到电子邮件应用！")
            }
        }

        binding.shareContainer.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    """
                    我正在使用《书虫》免费发现精品好书，你也来试试吧！
                    """.trimIndent()
                )
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.settingContainer.setOnClickListener {
            SettingsActivity.actionStart(requireContext())
        }

        binding.versionTextView.text =
            "Version(${if (BuildConfig.DEBUG) "DEBUG" else "RELEASE"}): v${BuildConfig.VERSION_NAME}"

        if (BuildConfig.DEBUG) {
            binding.debugToolContainer.visibility = View.VISIBLE
            binding.debugToolContainer.setOnClickListener {
                DebugHelperActivity.actionStart(this.requireContext())
            }
        }

        binding.userInfoContainer.setOnClickListener {
            if (!meViewModel.isLoggedIn) {
                LoginActivity.startForResult(this, REQ_START_LOGIN)
            }
        }

        meViewModel.currentUser.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.avatar.setActualImageResource(R.drawable.default_avatar)
                binding.nicknameTextView.text = "未登陆"
                binding.descriptionTextView.text = "说说你的看法吧……"
            } else {
                binding.avatar.setImageURI(it.avatar)
                binding.nicknameTextView.text = it.nickname ?: it.username
                binding.descriptionTextView.text = it.description
            }
        }
        meViewModel.refreshUserInfo()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_START_LOGIN -> {
                if (resultCode == Activity.RESULT_OK) {
                    meViewModel.refreshUserInfo()
                }
            }
        }
    }
}