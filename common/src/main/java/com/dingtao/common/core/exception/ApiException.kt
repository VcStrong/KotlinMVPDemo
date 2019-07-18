package com.dingtao.common.core.exception

/**
 * @author dingtao
 * @date 2019/1/2 7:01 PM
 * 封装了自定义的异常，页面拿着我的异常做出友好提示
 */
class ApiException : Exception {
    var code: String? = null//
    var displayMessage: String? = null//提示的消息

    constructor(code: String, displayMessage: String?) {
        this.code = code
        this.displayMessage = displayMessage
    }

    constructor(code: String, message: String, displayMessage: String) : super(message) {
        this.code = code
        this.displayMessage = displayMessage
    }
}
