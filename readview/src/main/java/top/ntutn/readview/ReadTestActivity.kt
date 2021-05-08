package top.ntutn.readview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import top.ntutn.commonutil.showLongToast
import top.ntutn.commonutil.showToast
import top.ntutn.readview.databinding.ActivityReadTestBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

//@Route(path = ReadTestActivity.PATH)
class ReadTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initData()
    }

    private fun initView() {
        binding.readView.setOnItemSelectListener(object : ReadView.OnItemSelectListener {
            override fun onSwitchPage(index: Int) = Unit
            override fun onPagePreviousClicked(isFirstPage: Boolean) {
                if (isFirstPage) {
                    "已是第一页".showLongToast()
                    return
                }
                binding.readView.goPreviousPage()
            }

            override fun onPageNextClicked(isLastPage: Boolean) {
                if (isLastPage) {
                    "已是最后一页".showLongToast()
                }
                //TODO 打开dialog
                binding.readView.goNextPage()
            }

            override fun onMenuClicked() {
                "弹出菜单".showToast()
            }
        })
    }

    private fun initData() {
        try {
            val `is` = resources.openRawResource(R.raw.mbook)
            val text = readTextFromSDcard(`is`)
            binding.readView.setText(text)
        } catch (e: java.lang.Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    @Throws(Exception::class)
    private fun readTextFromSDcard(`is`: InputStream): String {
        val reader = InputStreamReader(`is`)
        val bufferedReader = BufferedReader(reader)
        val builder = StringBuilder("")
        var str: String?
        while (bufferedReader.readLine().also { str = it } != null) {
            builder.append(str)
            builder.append("\n")
        }
        return builder.toString()
    }

    companion object {
        const val PATH = "/readview/test"

        fun actionStart(context: Context) {
            context.startActivity(Intent(context, ReadTestActivity::class.java))
        }
    }
}