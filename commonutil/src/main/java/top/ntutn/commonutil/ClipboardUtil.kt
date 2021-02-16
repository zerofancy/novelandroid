package top.ntutn.commonutil

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object ClipboardUtil {
    fun copyToClipboard(text: String) {
        val clipboard = AppUtil.getApplicationContext()
            .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText(javaClass.simpleName, text)
        clipboard.setPrimaryClip(clip)
    }
}