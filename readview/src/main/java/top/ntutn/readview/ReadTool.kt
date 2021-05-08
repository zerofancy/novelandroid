package top.ntutn.readview

import java.util.*


class ReadTool {
    var pageModels: MutableList<PageModel> = mutableListOf()
    var lineModels: MutableList<LineModel> = mutableListOf()
    private var stringList: MutableList<String> = mutableListOf()
    private var strXs: MutableList<Int> = mutableListOf()
    private var strColors: MutableList<Int> = mutableListOf()
    private var lineNum = 0

    fun initTool() {
        pageModels.clear()
        lineModels.clear()
        stringList.clear()
        strXs.clear()
        strColors.clear()
        lineNum = 0
    }

    fun addStrArr(subStr: String, strX: Int, strColor: Int) {
        stringList.add(subStr)
        strXs.add(strX)
        strColors.add(strColor)
    }

    fun setStrCaptal(strWidth: Int, strColor: Int) {
        for (i in 0..1) {
            stringList.add("")
            strXs.add(i * strWidth)
            strColors.add(strColor)
        }
    }

    fun addLine(strDiff: Int) {
        val lineModel = LineModel(stringList, strXs, strDiff, strColors)
        lineModels.add(lineModel)
        stringList = ArrayList()
        strXs = ArrayList()
        strColors = ArrayList()
    }

    fun addPage(readHeight: Int, fontSize: Int) {
        lineNum++
        if (lineNum * fontSize * 1.5 > readHeight) {
            val pageModel = PageModel(lineModels)
            pageModels.add(pageModel)
            lineModels = ArrayList()
            lineNum = 1
        }
    }

    fun addEnd(readHeight: Int, fontSize: Int) {
        addPage(readHeight, fontSize)
        addLine(0)
        val pageModel = PageModel(lineModels)
        pageModels.add(pageModel)
        lineNum = 1
    }
}
