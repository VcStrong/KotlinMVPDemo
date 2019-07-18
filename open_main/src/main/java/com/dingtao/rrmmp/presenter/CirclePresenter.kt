package com.dingtao.rrmmp.presenter

import com.dingtao.common.bean.Circle
import com.dingtao.common.bean.Result
import com.dingtao.common.core.presenter.WDPresenter
import com.dingtao.common.core.DataCall
import com.dingtao.common.core.http.IAppRequest

import io.reactivex.Observable

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */
class CirclePresenter(consumer: DataCall<List<Circle>>) : WDPresenter<IAppRequest, List<Circle>>(consumer) {

    var page = 1
        private set

    override fun getModel(args: List<Any>): Observable<Result<List<Circle>>> {
        val refresh = args.get(0) as Boolean
        if (refresh) {
            page = 1
        } else {
            page++
        }
        return iRequest.findCircleList(args[1] as Long, args[2] as String, page, 20)
    }


}
