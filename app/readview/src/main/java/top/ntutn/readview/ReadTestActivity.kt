package top.ntutn.readview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import top.ntutn.readview.databinding.ActivityReadTestBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@Route(path = ReadTestActivity.PATH)
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
            override fun onItemSelect(index: Int) {
                if (index == 0) {
                    binding.readView.goNextPage()
                } else {
                    binding.readView.goPreviousPage()
                }
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
            context.startActivity(Intent(context,ReadTestActivity::class.java))
        }
    }
}