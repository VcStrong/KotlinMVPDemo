package com.dingtao.common.bean.shop

/**
 * @author dingtao
 * @date 2019/1/3 10:49
 * qq:1940870847
 */
class Commodity {
    var commodityId: Long = 0
    lateinit var commodityName: String
    lateinit var masterPic: String
    var price: Double = 0.toDouble()
    var saleNum: Int = 0
}
