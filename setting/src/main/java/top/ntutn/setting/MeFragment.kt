package top.ntutn.setting

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.ntutn.commonui.base.BaseFragment
import top.ntutn.commonui.common.SimpleItemDecoration
import top.ntutn.commonui.common.SpacesItemDecoration
import top.ntutn.login.LoginServiceDelegate
import top.ntutn.commonui.common.viewLifecycle
import top.ntutn.commonutil.dp
import top.ntutn.commonutil.showSnackBar
import top.ntutn.commonutil.sp
import top.ntutn.setting.databinding.FragmentMeBinding

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
        val meSettingAdapter = MeSettingAdapter().apply {
            addSettingItem(
                MeSettingAdapter.MeSetting(
                    key = "connect_with_author",
                    title = "联系作者",
                    iconRes = R.drawable.ic_mail,
                ) {
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
            )
            addSettingItem(
                MeSettingAdapter.MeSetting(
                    key = "share",
                    iconRes = R.drawable.ic_share,
                    title = "分享"
                ) {
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
            )
            addSettingItem(
                MeSettingAdapter.MeSetting(
                    key = "setting",
                    iconRes = R.drawable.ic_setting,
                    title = "设置"
                ) {
                    SettingServiceDelegate.openSettingActivity(requireContext())
                }
            )
            addSettingItem(
                MeSettingAdapter.MeSetting(
                    key = "about",
                    iconRes = R.drawable.ic_info,
                    title = "关于"
                ) {
                    SettingServiceDelegate.openAboutActivity(requireContext())
                }
            )
            if (BuildConfig.DEBUG) {
                addSettingItem(
                    MeSettingAdapter.MeSetting(
                        key = "debug_tool",
                        iconRes = R.drawable.ic_setting_config,
                        title = "Debug工具"
                    ) {
                        DebugServiceDelegate.openDebugActivity(requireContext())
                    }
                )
            }
        }
        binding.meSettingRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = meSettingAdapter
            addItemDecoration(SpacesItemDecoration(6.dp, RecyclerView.VERTICAL))
        }

        binding.userInfoContainer.setOnClickListener {
            LoginServiceDelegate.startLoginActivity(this, REQ_START_LOGIN)
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