package top.ntutn

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.file.Files
import java.nio.file.Paths

object GenerateZeroConfigHolderTask {

    fun generateCode() {
        val gson = Gson()
        val classInfoMap: Map<String, ZeroConfigCompilerInformation> = try {
            val json = Files.readAllLines(Paths.get(JSON_PATH)).joinToString("\n")
            val type = object : TypeToken<Map<String, ZeroConfigCompilerInformation>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Throwable) {
            mutableMapOf()
        }
        val items = classInfoMap.map {
            """
            "${it.key}" to ZeroConfigInformation(
                key = "${it.key}",
                title = "${it.value.title}",
                clazz = "${it.value.clazz}",
                scope = "${it.value.scope}",
                owner = "${it.value.owner}"
              )
            """.trim()
        }.joinToString()
        val result = """
            package top.ntutn.zeroconfig.holder

            import top.ntutn.libzeroconfig.ZeroConfigInformation

            public object ZeroHolder {
                public val value: Map<String, ZeroConfigInformation> = mapOf($items)
            }
        """.trimIndent()

        val path = Paths.get(OUTPUT_PATH)
        Files.createDirectories(path)
        val outputFilePath = path.resolve("ZeroHolder.kt")
        Files.write(outputFilePath, result.toByteArray())
        Files.delete(Paths.get(JSON_PATH))
    }

    private const val JSON_PATH = "build/zeroconfig.json"
    private const val OUTPUT_PATH =
        "zeroconfigutil/build/generated/source/zeroHolder/top/ntutn/zeroconfig/holder"
}

data class ZeroConfigCompilerInformation(
    val key: String,
    val title: String,
    val clazz: String,
    val scope: String,
    val owner: String
)