package top.ntutn.readview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.ceil
import kotlin.math.min

class BreakReadView : AppCompatTextView {
    companion object {
        private val PAGE_LAST_TEXT = "\n" + "\u3000".repeat(1000) + "."
    }

    private var currentWidth = 0
    private var currentHeight = 0
    private var textChanging = false
    private var originString = ""
    private var pageLines = 0 // 每页行数
    private var totalPageNumber = 0 // 当前布局下总页数
    private var currentPageNumber = 0 // 当前阅读页数
    private var lines = mutableListOf<String>() //切分好的小说
    private val clickEventListenerList = mutableListOf<Pair<RectF, () -> Unit>>()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun changeFont(path: String) {
        val typeface = Typeface.createFromAsset(context.assets, path)
        setTypeface(typeface)
        rebreakString()
    }

    /**
     * @param textSize 单位sp
     */
    fun changeTextSize(textSize: Float) {
        setTextSize(textSize)
        rebreakString()
    }

    private fun getTextViewLines(): Int {
        val topOfLastLine = currentHeight - lineHeight - paddingTop - paddingBottom
        text
        val lines = layout.getLineForVertical(topOfLastLine) //staticLayout.lineCount
        return if (maxLines > lines) lines else maxLines
    }

    private fun breakStringAllowEnter(str: String, width: Int): MutableList<String> {
        val res = mutableListOf<String>()
        str.split("\n").forEach {
            res.addAll(breakString("\u3000\u3000${it.trim()}", width))
        }
        return res
    }

    private fun breakString(str: String, width: Int): MutableList<String> {
        val res = mutableListOf<String>()
        var len = 0
        var start: Int
        var end = 0
        while (end < str.length) {
            len += end
            val count: Int = paint.breakText(str, end, str.length, true, width.toFloat(), null)
            start = end
            end += count
            res.add(str.substring(start, end))
        }
        return res
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        if (textChanging) {
            super.setText(text, type)
            return
        }
        originString = text.toString()
        textChanging = true
        super.setText(originString + PAGE_LAST_TEXT, type) // 附加一些空行，避免页面内容不满
        textChanging = false
        if (currentWidth > 0) {
            rebreakString()
        }
    }

    @SuppressLint("SetTextI18n", "DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (currentWidth == measuredWidth && currentHeight == measuredHeight) return
        currentHeight = measuredHeight
        // 高度或宽度改变后重新计算页面行数
        pageLines = getTextViewLines()
        currentWidth = measuredWidth

        rebreakString()
    }

    /**
     * 重新计算行数据
     */
    private fun rebreakString() {
        lines = breakStringAllowEnter(originString, currentWidth - paddingLeft - paddingRight)
        pageLines = getTextViewLines()
        totalPageNumber = ceil(lines.size.toFloat() / pageLines.toFloat()).toInt()
        computeCurrentPageText()
    }

    fun getPageCount(): Int {
        if (pageLines == 0) {
            return 1
        }
        return lines.size / pageLines + if (lines.size % pageLines > 0) 1 else 0
    }

    fun getCurrentPage() = currentPageNumber

    fun setCurrentPage(pageNumber: Int) {
        val pageNumber = when {
            pageNumber < 0 -> 0
            pageNumber > getPageCount() - 1 -> getPageCount() - 1
            else -> pageNumber
        }
        currentPageNumber = pageNumber
        computeCurrentPageText()
    }

    fun getCurrentLineNumber() = pageLines * currentPageNumber

    fun jumpToLineNumber(lineNumber: Int) {
        val pageNumber = lineNumber / pageLines + if (lineNumber % pageLines > 0) 1 else 0
        currentPageNumber = pageNumber
        computeCurrentPageText()
    }

    fun isFirstPage() = currentPageNumber == 0

    fun isLastPage() = currentPageNumber == getPageCount() - 1

    fun goPrevPage() {
        if (!isFirstPage()) {
            currentPageNumber--
            computeCurrentPageText()
        }
    }

    fun goNextPage() {
        if (!isLastPage()) {
            currentPageNumber++
            computeCurrentPageText()
        }
    }

    fun addClickEventListener(rectF: RectF, block: () -> Unit) {
        clickEventListenerList.add(rectF to block)
    }

    fun clearClickEventListener() {
        clickEventListenerList.clear()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_UP -> {
                clickEventListenerList.forEach {
                    if (it.first.contains(event.x, event.y)) {
                        it.second.invoke()
                        return true
                    }
                }
                return false
            }
        }
        return false
    }

    /**
     * 计算当前页面文字
     */
    private fun computeCurrentPageText() {
        if (lines.isNullOrEmpty() || pageLines <= 0) {
            return
        }
        if (currentPageNumber < 0 || currentPageNumber > totalPageNumber - 1) {
            currentPageNumber = 0
        }
        textChanging = true
        text = lines.subList(
            pageLines * currentPageNumber,
            min(pageLines * currentPageNumber + pageLines, lines.size)
        ).joinToString("\n") + PAGE_LAST_TEXT // 附加一些空行，避免当前页面内容不满
        textChanging = false
    }
}