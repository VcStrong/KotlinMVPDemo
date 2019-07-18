package com.dingtao.common.core.http

import com.dingtao.common.bean.Banner
import com.dingtao.common.bean.Circle
import com.dingtao.common.bean.Result
import com.dingtao.common.bean.UserInfo
import com.dingtao.common.bean.shop.HomeList

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author dingtao
 * @date 2018/12/28 10:00
 * qq:1940870847
 */
interface IAppRequest {

    @FormUrlEncoded
    @POST("user/v1/register")
    fun register(@Field("phone") m: String, @Field("pwd") p: String): Observable<Result<Any>>

    @FormUrlEncoded
    @POST("user/v1/login")
    fun login(@Field("phone") m: String, @Field("pwd") p: String): Observable<Result<UserInfo>>

    /**
     * banner
     */
    @GET("commodity/v1/bannerShow")
    fun bannerShow(): Observable<Result<List<Banner>>>

    /**
     * 首页商品列表
     */
    @GET("commodity/v1/commodityList")
    fun commodityList(): Observable<Result<HomeList>>

    /**
     * 圈子
     */
    @GET("circle/v1/findCircleList")
    fun findCircleList(
            @Header("userId") userId: Long,
            @Header("sessionId") sessionId: String,
            @Query("page") page: Int,
            @Query("count") count: Int): Observable<Result<List<Circle>>>

    /**
     * 圈子
     */
    @FormUrlEncoded
    @POST("user/findCircle/{uid}")
    fun findCircle(
            @Path("uid") uid: Int,
            @Field("page") page: Int,
            @Field("count") count: Int): Observable<Result<List<Circle>>>

    /**
     * 圈子点赞
     */
    @FormUrlEncoded
    @POST("circle/verify/v1/addCircleGreat")
    fun addCircleGreat(
            @Header("userId") userId: String,
            @Header("sessionId") sessionId: String,
            @Field("circleId") circleId: Long): Observable<Result<Any>>

    /**
     * 我的足迹
     */
    @GET("commodity/verify/v1/browseList")
    fun browseList(
            @Header("userId") userId: String,
            @Header("sessionId") sessionId: String,
            @Query("page") page: Int,
            @Query("count") count: Int): Observable<Result<List<Banner>>>

    /**
     * 同步购物车数据
     */
    @PUT("order/verify/v1/syncShoppingCart")
    fun syncShoppingCart(
            @Header("userId") userId: String,
            @Header("sessionId") sessionId: String,
            @Body data: String): Observable<Result<Any>>

    /**
     * 发布圈子
     */
    @POST("circle/verify/v1/releaseCircle")
    fun releaseCircle(@Header("userId") userId: Long,
                      @Header("sessionId") sessionId: String,
                      @Body body: MultipartBody): Observable<Result<Any>>
}
