package com.peakmain.debug.manager

import com.peakmain.debug.bean.HttpLoggingBean

/**
 * author ：Peakmain
 * createTime：2023/12/22
 * mail:2726449200@qq.com
 * describe：
 */
class HttpLoggingManager private constructor(){
    private var mHttpLoggingList: MutableList<HttpLoggingBean> = ArrayList()
    companion object{
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            HttpLoggingManager()
        }
    }

    fun addFirst(httpLoggingBean: HttpLoggingBean){
        mHttpLoggingList.apply {
            if (size > 100) {
                removeAt(size - 1)
            }
        }
        mHttpLoggingList.add(0, httpLoggingBean)
    }

    fun getHttpLoggingList():MutableList<HttpLoggingBean>{
        return mHttpLoggingList
    }


}