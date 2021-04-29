package top.ntutn.readview

import android.content.Context
import android.content.Intent
import android.graphics.RectF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import top.ntutn.readview.databinding.ActivityBreakReadTestBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class BreakReadTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBreakReadTestBinding

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, BreakReadTestActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreakReadTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initData()
    }

    private fun initView() {
        binding.readView.post {
            binding.readView.apply {
                addClickEventListener(RectF(0f, 0f, (width / 3).toFloat(), height.toFloat())) {
                    goPrevPage()
                }
                addClickEventListener(
                    RectF(
                        (width / 3 * 2).toFloat(),
                        0f,
                        width.toFloat(),
                        height.toFloat()
                    )
                ) {
                    goNextPage()
                }
            }
        }
    }

    private fun initData() {
        try {
            val inputStream = resources.openRawResource(R.raw.mbook)
            val text = readTextFromSDcard(inputStream)
            binding.readView.text = text
        } catch (e: java.lang.Exception) {
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
}