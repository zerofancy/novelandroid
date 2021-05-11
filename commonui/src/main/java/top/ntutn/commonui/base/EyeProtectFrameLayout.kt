package top.ntutn.commonui.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorInt

class EyeProtectFrameLayout : FrameLayout {
    var eyeProtectColor = EyeProtectColor.NONE

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        canvas.drawColor(eyeProtectColor.color)
    }

    enum class EyeProtectColor(@ColorInt val color: Int) {
        NONE(Color.argb(0, 0, 0, 0)),
        BROWN(Color.argb(68, 129, 116, 38)),
        GREEN(Color.argb(68, 199, 237, 204))
    }
}