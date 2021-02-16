package top.ntutn.novelrecommend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import top.ntutn.novelrecommend.model.NovelModel

interface NovelService {
    @GET("api/novelRecommend")
    fun getNovel(@QueryMap deviceInfo: Map<String, String>): Call<List<NovelModel>>
}