package com.peakmain.debugtools

import com.peakmain.basiclibrary.network.status.ApiStatus
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

/**
 * author ：Peakmain
 * createTime：2022/5/16
 * mail:2726449200@qq.com
 * describe：
 */
interface WanAndroidService {
    @GET("banner/json")
    fun getBannerJson(): Observable<Any>
}