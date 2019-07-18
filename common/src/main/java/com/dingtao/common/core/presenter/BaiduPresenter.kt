package com.dingtao.common.core.presenter

import com.dingtao.common.bean.BDResult
import com.dingtao.common.core.DataCall
import com.dingtao.common.core.exception.ApiException
import com.dingtao.common.core.exception.CustomException
import com.dingtao.common.core.http.NetworkManager

import java.lang.reflect.ParameterizedType

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author dingtao
 * @date 2018/12/28 11:30
 * qq:1940870847
 * 泛型T用来区分不同模块的请求
 * 泛型E用来代表数据
 */
abstract class BaiduPresenter<T,E> constructor(private val dataCall: DataCall<E>) {

    /**
     * 是否正在运行
     */
    var isRunning: Boolean = false//是否运行，防止重复请求，这里没有用rxjava的重复过滤（个人感觉重复过滤用在多次点击上比较好，请求从体验角度最好不要间隔过滤）
    private var disposable: Disposable? = null//rxjava层取消请求
    protected var iRequest: T //区分不同模块的请求

    /**
     * 获取泛型对相应的Class对象
     * @return
     */
    private//返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
    //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
    //<T>
    val tClass: Class<T>
        get() {
            val type = this.javaClass.getGenericSuperclass() as ParameterizedType
            return type.actualTypeArguments[0] as Class<T>
        }


    init {
        iRequest = NetworkManager.instance().create(NetworkManager.REQUEST_TYPE_DEFAULT, tClass)
    }

    protected abstract fun getModel(args: List<Any>): Observable<BDResult<E>>

    fun reqeust(vararg args: Any) {
        if (isRunning) {
            return
        }

        isRunning = true
        val observable = getModel(args.asList())

        disposable = observable.subscribeOn(Schedulers.io())//将请求调度到子线程上
                .observeOn(AndroidSchedulers.mainThread())//观察响应结果，把响应结果调度到主线程中处理
                .subscribe( {result ->
                    isRunning = false
                    if (result.code == 0) {
                        result.data?.let { dataCall.success(it, args.asList()) }
                    } else {
                        dataCall.fail(ApiException(result.code.toString(), result.msg),args.asList())
                    }},
                        ({ e -> dataCall.fail(CustomException.handleException(e), args.asList()) }))
    }

    /**
     * 取消请求
     */
    fun cancelRequest() {
        isRunning = false
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}
