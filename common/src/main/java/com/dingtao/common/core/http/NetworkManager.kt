package com.dingtao.common.core.http

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author dingtao
 * @date 2018/12/28 10:07
 * qq:1940870847
 */
class NetworkManager private constructor() {
    private var app_retrofit: Retrofit? = null
    private var baidu_retrofit: Retrofit? = null

    init {
        init()
    }

    private fun init() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY//打印请求参数，请求结果

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                //                .addInterceptor(new Interceptor() {
                //                    @Override
                //                    public Response intercept(Chain chain) throws IOException {
                //                        UserInfoDao userInfoDao = DaoMaster.newDevSession(WDApplication.getContext(),UserInfoDao.TABLENAME).getUserInfoDao();
                //                        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
                //                        UserInfo userInfo = userInfos.get(0);//读取第一项
                //                        Request request = chain.request().newBuilder()
                //                                .addHeader("userId",userInfo.getUserId()+"")
                //                                .addHeader("sessionId",userInfo.getSessionId())
                //                                .build();
                //                        return chain.proceed(request);
                //                    }
                //                })
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()

        app_retrofit = Retrofit.Builder()
                .client(okHttpClient)
                //                .baseUrl("http://169.254.101.220:8080/")//base_url:http+域名
                //                .baseUrl("http://172.17.8.100/small/")//base_url:http+域名
                .baseUrl("http://mobile.bwstudent.com/small/")//base_url:http+域名
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用Rxjava对回调数据进行处理
                .addConverterFactory(GsonConverterFactory.create())//响应结果的解析器，包含gson，xml，protobuf
                .build()

        baidu_retrofit = Retrofit.Builder()
                .client(okHttpClient)
                //                .baseUrl("http://169.254.101.220:8080/")//base_url:http+域名
                //                .baseUrl("http://172.17.8.100/small/")//base_url:http+域名
                .baseUrl("http://mobile.bwstudent.com/small/")//base_url:http+域名
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用Rxjava对回调数据进行处理
                .addConverterFactory(GsonConverterFactory.create())//响应结果的解析器，包含gson，xml，protobuf
                .build()
    }

    fun <T> create(requestType: Int, service: Class<T>): T {
        return if (requestType == REQUEST_TYPE_SDK_BD) {//如果请求百度SDK的接口
            baidu_retrofit!!.create(service)
        } else app_retrofit!!.create(service)
    }

    companion object {
        val REQUEST_TYPE_DEFAULT: Int = 0
        val REQUEST_TYPE_SDK_BD: Int = 2

        private var instance: NetworkManager? = null

        fun instance(): NetworkManager {
            if (instance == null) {
                instance = NetworkManager()
            }
            return instance as NetworkManager
        }
    }

}
