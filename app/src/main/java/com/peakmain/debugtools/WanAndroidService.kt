package com.peakmain.debugtools

import io.reactivex.Observable
import retrofit2.http.*


/**
 * author ：Peakmain
 * createTime：2022/5/16
 * mail:2726449200@qq.com
 * describe：
 */
interface WanAndroidService {
    @GET("banner/json")
    fun getBannerJson(): Observable<Any>

    @GET("project/list/{id}/json/")
    fun getListJson(@Path("id") id: Int, @Query("cid") cid: Int): Observable<Any>

    @FormUrlEncoded
    @POST("user/login")
    fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<Any>
}