package com.dingtao.common.bean

/**
 * @author dingtao
 * @date 2018/12/28 10:05
 * qq:1940870847
 */
class BDResult<T> : Any() {
    var code: Int = 0
    lateinit var msg: String
    var data: T? = null
}
