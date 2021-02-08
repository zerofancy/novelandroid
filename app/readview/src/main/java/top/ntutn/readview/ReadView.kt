package top.ntutn.readview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ReadView : View {
    private var lineNum = 1
    private var lineHeight = 8
    private var viewWidth = 0
    private var viewHeight = 0
    private var textWidth = 0
    private var textHeight = 0
    private var readWidth = 0
    private var readHeight = 0
    private var fontSize = 24
    private var textColor = Color.BLACK
    private var background = 0
    private lateinit var readTool: ReadTool
    private lateinit var chapterModel: ChapterModel
    private var mPaint: Paint = Paint()
    private var eBook: String = ""

    //定义一个接口对象listerner
    private var listener: OnItemSelectListener? = null
    private lateinit var bitmap: Bitmap

    constructor(context: Context?) : this(context!!, null) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initCustomAttrs(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initCustomAttrs(context, attrs)
    }

    /**
     * 获取自定义属性
     */
    private fun initCustomAttrs(context: Context, attrs: AttributeSet?) {
        //获取自定义属性。
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ReadView)
        //获取字体大小,默认大小是16dp
        fontSize = ta.getDimension(R.styleable.ReadView_fontSize, 24f).toInt()
        //获取文字内容
        eBook = ta.getString(R.styleable.ReadView_text).toString().trimIndent()
        //获取文字颜色，默认颜色是BLUE
        textColor = ta.getColor(R.styleable.ReadView_color, Color.BLACK)
        //获取背景
        background = ta.getResourceId(R.styleable.ReadView_background, R.drawable.paper)
        ta.recycle()
        bitmap = BitmapFactory.decodeResource(this.resources, background)
        // 设置画笔颜色
        mPaint.color = textColor
        // 设置画笔宽度
        mPaint.textSize = fontSize.toFloat()
        mPaint.isAntiAlias = true
        mPaint.isFilterBitmap = true
        chapterModel = ChapterModel(listOf(), 0)
        readTool = ReadTool()
        setMatrix()
    }

    fun setText(str: String) {
        eBook = str.trimIndent()
        requestLayout()
        invalidate()
    }

    fun goPreviousPage() {
        if (chapterModel.index - 1 >= 0) {
            chapterModel.index = chapterModel.index - 1
        }
        invalidate()
    }

    fun goNextPage() {
        if (chapterModel.index + 1 < chapterModel.pageModels.size) {
            chapterModel.index = chapterModel.index + 1
        }
        invalidate()
    }

    private fun setMatrix() {
        val bitmapWidth = bitmap.width.toFloat()
        val bitmapHeight = bitmap.height.toFloat()
        val scaleX = viewWidth / bitmapWidth
        val scaleY = viewHeight / bitmapHeight
        matrix.postTranslate(0f, 0f)
        matrix.preScale(scaleX, scaleY)
    }

    private fun getStrData(str: String) {
        readTool.initTool()
        readTool.setStrCaptal(fontSize, textColor)
        var lineWidth = 2 * fontSize
        for (i in str.indices) {
            val subStr: String = if (i < str.length - 1) {
                str.substring(i, i + 1)
            } else {
                str.substring(i)
            }
            val fontWidth = mPaint.measureText(subStr).toInt()
            lineWidth += fontWidth
            when {
                subStr == "\n" -> {
                    readTool.addPage(readHeight, fontSize)
                    readTool.addLine(0)
                    readTool.setStrCaptal(fontSize, textColor)
                    lineWidth = 2 * fontSize
                }
                lineWidth < readWidth -> {
                    readTool.addStrArr(subStr, lineWidth - fontWidth, textColor)
                }
                else -> {
                    readTool.addPage(readHeight, fontSize)
                    readTool.addLine(readWidth - lineWidth + fontWidth)
                    lineWidth = fontWidth
                    readTool.addStrArr(subStr, 0, textColor)
                }
            }
        }
        readTool.addEnd(readHeight, fontSize)
        lineWidth = 0
        chapterModel.pageModels = readTool.pageModels
        lineNum = readTool.lineModels.size
        textWidth = if (lineNum > 1) {
            width
        } else {
            lineWidth
        }
        textHeight = lineNum * (fontSize + lineHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        viewWidth = widthSize
        viewHeight = heightSize
        readWidth = viewWidth - paddingLeft - paddingRight
        readHeight = viewHeight - paddingTop - paddingBottom
        getStrData(eBook)
        val width: Int = if (widthMode == MeasureSpec.UNSPECIFIED) {
            textWidth
        } else {
            widthSize
        }
        val height: Int = if (heightMode == MeasureSpec.UNSPECIFIED) {
            textHeight
        } else {
            heightSize
        }
        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val page: PageModel = chapterModel.pageModels[chapterModel.index]
        canvas.drawBitmap(
            bitmap,
            Rect(0, 0, bitmap.width, bitmap.height),
            Rect(0, 0, width, height),
            mPaint
        )
        for (i in page.lineModels.indices) {
            val line: LineModel = page.lineModels[i]
            val num: Int = line.stringList.size
            val spacing: Float = if (num == 0) {
                0f
            } else {
                line.strDiff / (num - 1).toFloat()
            }
            for (j in 0 until num) {
                mPaint.color = line.strColors[j]
                canvas.drawText(
                    line.stringList[j], line.strX[j] + paddingLeft + j * spacing,
                    (i + 1) * fontSize * 1.5f + paddingTop - 4, mPaint
                )
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_MOVE -> false
            MotionEvent.ACTION_DOWN -> true
            MotionEvent.ACTION_UP -> {
                when (event.x) {
                    in 0f..(viewWidth / 3).toFloat() -> listener?.onPagePreviousClicked()
                    in (viewWidth / 3 * 2).toFloat()..viewWidth.toFloat() -> listener?.onPageNextClicked()
                    else -> listener?.onMenuClicked()
                }
                true
            }
            else -> false
        }
    }

    //获得接口对象的方法。
    fun setOnItemSelectListener(listener: OnItemSelectListener?) {
        this.listener = listener
    }

    //定义一个接口
    interface OnItemSelectListener {
        fun onPagePreviousClicked()

        fun onPageNextClicked()

        fun onMenuClicked()
    }
}