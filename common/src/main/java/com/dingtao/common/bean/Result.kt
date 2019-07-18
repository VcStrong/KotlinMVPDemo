package com.dingtao.common.bean

/**
 * @author dingtao
 * @date 2018/12/28 10:05
 * qq:1940870847
 */
class Result<T>(var status: String, var message: String) {
    var result: T? = null
    lateinit var headPath: String
}
