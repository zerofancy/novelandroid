package top.ntutn.novelrecommend.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import top.ntutn.novelrecommend.databinding.ActivityDebugHelperBinding

class DebugHelperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDebugHelperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebugHelperBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, DebugHelperActivity::class.java)
            context.startActivity(intent)
        }
    }
}