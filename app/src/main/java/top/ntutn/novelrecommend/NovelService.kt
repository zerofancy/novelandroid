package top.ntutn.novelrecommend

import retrofit2.Call
import retrofit2.http.GET
import top.ntutn.novelrecommend.model.NovelModel

interface NovelService {
    @GET("api/novelRecommend")
    fun getNovel():Call<List<NovelModel>>
}