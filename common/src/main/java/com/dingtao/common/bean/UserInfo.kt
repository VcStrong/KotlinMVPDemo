package com.dingtao.common.bean

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id
import org.greenrobot.greendao.annotation.Generated

/**
 * @author dingtao
 * @date 2018/12/29 14:29
 * qq:1940870847
 */
@Entity
class UserInfo {
    @Id
    var userId: Long = 0
    lateinit var headPic: String
    lateinit var nickName: String
    lateinit var phone: String
    lateinit var sessionId: String
    var sex: Int = 0

    var status: Int = 0//记录本地用户登录状态，用于直接登录和退出,1:登录，0：未登录或退出

    @Generated(hash = 836882664)
    constructor(userId: Long, headPic: String, nickName: String, phone: String,
                sessionId: String, sex: Int, status: Int) {
        this.userId = userId
        this.headPic = headPic
        this.nickName = nickName
        this.phone = phone
        this.sessionId = sessionId
        this.sex = sex
        this.status = status
    }

    @Generated(hash = 1279772520)
    constructor() {
    }


}
