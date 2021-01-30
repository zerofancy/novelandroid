package top.ntutn.libzeroconfigcompiler

import com.google.auto.service.AutoService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import top.ntutn.libzeroconfig.DefaultScope
import top.ntutn.libzeroconfig.ZeroConfig
import top.ntutn.libzeroconfig.ZeroConfigInformation
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic
import javax.tools.StandardLocation


@AutoService(Processor::class)
class ZeroConfigProcessor : AbstractProcessor() {

    //元素相关的工具类
    private lateinit var elementUtils: Elements

    //文件相关的工具类
    private lateinit var filer: Filer

    /**
     * 日志相关的工具类
     */
    private lateinit var messager: Messager

    /**
     * 类型相关工具类
     */
    private lateinit var typeUtils: Types

    private lateinit var classInfoMap: MutableMap<String, ZeroConfigCompilerInformation>
    private val gson = Gson()

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        elementUtils = processingEnv.elementUtils
        filer = processingEnv.filer
        messager = processingEnv.messager
        typeUtils = processingEnv.typeUtils
        note("${javaClass.simpleName} init")
        classInfoMap = try {
            val json = Files.readAllLines(Paths.get(JSON_PATH)).joinToString("\n")
            note(json)
            val type = object : TypeToken<Map<String, ZeroConfigCompilerInformation>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Throwable) {
            mutableMapOf()
        }
        note(classInfoMap.toString())
    }

    override fun process(
        p0: MutableSet<out TypeElement>,
        roundEnvironment: RoundEnvironment
    ): Boolean {
        roundEnvironment.getElementsAnnotatedWith(ZeroConfig::class.java).forEach { element ->
            //使用了注解的某个类
            if (element !is TypeElement) {
                error("注解只能标记在实体类上：$element")
                return false
            }
            val annotation = element.getAnnotation(ZeroConfig::class.java)
            if (!checkAnnotationValid(annotation)) return false
            classInfoMap[annotation.key] = ZeroConfigCompilerInformation(
                key = annotation.key,
                clazz = element.qualifiedName.toString(),
                title = annotation.title,
                scope = getClassFromAnnotation { annotation.scope.qualifiedName!! },
                owner = annotation.owner
            )
        }
//        generateCode(classInfoMap)
        saveJsonResult()
        return false
    }

    private fun saveJsonResult() {
        Files.write(Paths.get(JSON_PATH), gson.toJson(classInfoMap).toByteArray())
    }

    private fun generateCode(classInfoMap: Map<String, ZeroConfigCompilerInformation>) {
        note("发现${classInfoMap.size}个${ZeroConfig::class.qualifiedName}注解")
        val file = FileSpec.builder(HOLDER_PACKAGE_NAME, OUTPUT_CLASSNAME)
            .addType(
                TypeSpec.objectBuilder(OUTPUT_CLASSNAME)
                    .addProperty(
                        PropertySpec.builder(
                            "value",
                            Map::class.parameterizedBy(String::class)
                                .plusParameter(ZeroConfigInformation::class)
                        )
                            .initializer(
                                "mapOf(${
                                    classInfoMap.map {
                                        "\"${it.key}\" to ZeroConfigInformation(key=\"${it.value.key}\",title=\"${it.value.title}\",clazz=${it.value.clazz}::class,scope=${it.value.scope}::class,owner=\"${it.value.owner}\")"
                                    }.joinToString(",")
                                })"
                            ).build()
                    ).build()
            ).build()
        file.writeTo(trickyCreateResource(HOLDER_PACKAGE_NAME, "${OUTPUT_CLASSNAME}.kt"))
        note(file.toString())
    }

    @Deprecated("tricky")
    private fun trickyCreateResource(packageName: String, fileName: String): File {
        val filerFile = filer.createResource(
            StandardLocation.SOURCE_OUTPUT,
            packageName,
            "${UUID.randomUUID()}.tmp"
        )
        val generatedResourcePath: Path = Paths.get(filerFile.toUri()).parent
        val targetPath = generatedResourcePath.resolve(fileName)
        note("输出到$targetPath")
        note(File(".").canonicalPath)
        return targetPath.toFile()
    }

    private fun checkAnnotationValid(annotation: ZeroConfig): Boolean {
        if (annotation.key.trim() == "") {
            error("key不能为空：$annotation")
            return false
        }
        if (annotation.title.trim() == "") {
            warning("title为空：$annotation")
        }
        if (annotation.owner.trim() == "") {
            error("必须指定owner：$annotation")
            return false
        }
        if (getClassFromAnnotation { annotation.scope.qualifiedName!! } == DefaultScope::class.qualifiedName) {
            warning("建议指定业务线：$annotation")
        }
        return true
    }

    /**
     * 获取annotation中的Class
     * https://www.jianshu.com/p/6822278f4771
     */
    private fun getClassFromAnnotation(block: () -> String): String {
        return try {
            block()
        } catch (e: MirroredTypeException) {
            e.typeMirror.toString()
        }
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> =
        mutableSetOf(ZeroConfig::class.java.canonicalName)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    fun note(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "$message\r\n")
    }

    // \r\n换行 https://medium.com/@cafonsomota/annotation-processor-printing-a-message-and-doing-it-in-a-new-line-1b6609e86e5c
    fun warning(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "$message\r\n")
    }

    fun error(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "$message\r\n")
    }

    companion object {
        // 生成的代码所在的包名
        private const val HOLDER_PACKAGE_NAME = "top.ntutn.zeroconfig.holder"
        private const val OUTPUT_CLASSNAME = "ZeroHolder"
        private const val JSON_PATH = "build/zeroconfig.json"
    }
}