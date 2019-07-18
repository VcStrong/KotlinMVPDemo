package com.dingtao.common.bean

/**
 * @author dingtao
 * @date 2019/1/3 21:40
 * qq:1940870847
 */
class Circle {
    var id: Long = 0
    var userId: Long = 0
    lateinit var nickName: String
    lateinit var headPic: String
    var commodityId: Long = 0
    lateinit var content: String
    lateinit var image: String
    var whetherGreat: Int = 0//当前登录用户是否点赞(2为否，1为是)
    var greatNum: Int = 0
    var createTime: Long = 0
}
