package top.ntutn.commonutil

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * 简化Toat写法
 */
fun String.showToast() {
    Toast.makeText(AppUtil.getApplicationContext(), this, Toast.LENGTH_SHORT).show()
}

fun String.showLongToast() {
    Toast.makeText(AppUtil.getApplicationContext(), this, Toast.LENGTH_LONG).show()
}

fun Int.showToast() {
    Toast.makeText(AppUtil.getApplicationContext(), this, Toast.LENGTH_SHORT).show()
}

fun Int.showLongToast() {
    Toast.makeText(AppUtil.getApplicationContext(), this, Toast.LENGTH_LONG).show()
}

fun View.showSnackBar(text: String, actionText: String? = null, block: () -> Unit = {}) {
    val snackbar = Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
    actionText?.let {
        snackbar.setAction(actionText) { block() }
    }
    snackbar.show()
}

fun View.showSnackBar(
    @StringRes text: Int,
    @StringRes actionText: Int? = null,
    block: () -> Unit = {}
) {
    val snackbar = Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
    actionText?.let {
        snackbar.setAction(actionText) { block() }
    }
    snackbar.show()
}