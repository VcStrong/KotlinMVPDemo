package com.dingtao.rrmmp.presenter

import com.dingtao.common.bean.Result
import com.dingtao.common.bean.shop.HomeList
import com.dingtao.common.core.presenter.WDPresenter
import com.dingtao.common.core.DataCall
import com.dingtao.common.core.http.IAppRequest

import io.reactivex.Observable

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */
class HomeListPresenter(dataCall: DataCall<HomeList>) : WDPresenter<IAppRequest, HomeList>(dataCall) {

    override fun getModel(args: List<Any>): Observable<Result<HomeList>> {
        return iRequest.commodityList()
    }


}
