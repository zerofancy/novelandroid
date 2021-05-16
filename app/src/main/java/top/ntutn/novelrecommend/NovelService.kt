package top.ntutn.novelrecommend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import top.ntutn.novelrecommend.model.ChapterModel
import top.ntutn.novelrecommend.model.NovelModel

interface NovelService {
    /**
     * 推荐几篇小说
     */
    @GET("api/novelRecommend")
    fun getRandomNovelInfo(@QueryMap deviceInfo: Map<String, String>): Call<List<NovelModel>>

    /**
     * 获取指定小说的信息
     */
    @GET("api/novel/{nid}/info")
    fun getNovelInfo(@Path("nid") novelId: Long): Call<NovelModel>

    @GET("api/novel/{nid}/chapter/{cid}")
    fun getChapter(@Path("nid") novelId: Long, @Path("cid") chapterId: Int): Call<ChapterModel>
}