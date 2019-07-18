package com.dingtao.rrmmp.fragment

import android.content.Context
import android.net.Uri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View

import com.dingtao.rrmmp.R
import com.dingtao.rrmmp.adapter.CommodityAdpater
import com.dingtao.common.bean.Banner
import com.dingtao.common.bean.shop.HomeList
import com.dingtao.common.core.DataCall
import com.dingtao.common.core.WDFragment
import com.dingtao.common.core.exception.ApiException
import com.dingtao.rrmmp.presenter.BannerPresenter
import com.dingtao.rrmmp.presenter.HomeListPresenter
import com.dingtao.common.util.recyclerview.SpacingItemDecoration
import com.facebook.drawee.view.SimpleDraweeView
import com.zhouwei.mzbanner.MZBannerView
import com.zhouwei.mzbanner.holder.MZViewHolder
import kotlinx.android.synthetic.main.frag_main.view.*

/**
 * @author dingtao
 * @date 2019/1/2 10:28
 * qq:1940870847
 */
class HomeFragment : WDFragment() {

    private var mHotAdapter: CommodityAdpater? = null
    private var mFashionAdapter: CommodityAdpater? = null
    private var mLifeAdapter: CommodityAdpater? = null

    lateinit internal var bannerPresenter: BannerPresenter
    lateinit internal var homeListPresenter: HomeListPresenter
    var mBanner:MZBannerView<Banner>? = null

    override val pageName: String = "首页Fragment"

    override val layoutId: Int = R.layout.frag_main
    var mHotList:RecyclerView?=null
    var mFashionList:RecyclerView?=null
    var mLifeList:RecyclerView?=null

    override fun initView(view:View) {
        mBanner = view.findViewById(R.id.mBanner)

        mHotAdapter = CommodityAdpater(context!!, CommodityAdpater.HOT_TYPE)
        mFashionAdapter = CommodityAdpater(context!!, CommodityAdpater.FASHION_TYPE)
        mLifeAdapter = CommodityAdpater(context!!, CommodityAdpater.HOT_TYPE)

        mHotList = view.mHotList
        mFashionList = view.mFashionList
        mLifeList = view.mLifeList

        mHotList!!.layoutManager = GridLayoutManager(context, 3)
        mFashionList!!.layoutManager = GridLayoutManager(context, 1)
        mLifeList!!.layoutManager = GridLayoutManager(context, 2)

        val space = resources.getDimensionPixelSize(R.dimen.dip_10)

        mHotList!!.addItemDecoration(SpacingItemDecoration(space))
        mFashionList!!.addItemDecoration(SpacingItemDecoration(space))
        mLifeList!!.addItemDecoration(SpacingItemDecoration(space))

        mHotList!!.adapter = mHotAdapter
        mFashionList!!.adapter = mFashionAdapter
        mLifeList!!.adapter = mLifeAdapter

        // 设置数据
        bannerPresenter = BannerPresenter(MyBanner())

        bannerPresenter.reqeust()

        homeListPresenter = HomeListPresenter(HomeCall())
        homeListPresenter.reqeust()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBanner!!.pause()
    }

    /**
     * @author dingtao
     * @date 2019/1/3 9:21 AM
     * banner
     */
    internal inner class BannerViewHolder : MZViewHolder<Banner> {
        private var mImageView: SimpleDraweeView? = null

        override fun createView(context: Context): View {
            // 返回页面布局
            val view = LayoutInflater.from(context).inflate(R.layout.banner_item, null)
            mImageView = view.findViewById(R.id.banner_image)
            return view
        }

        override fun onBind(context: Context, position: Int, data: Banner) {
            // 数据绑定
            mImageView!!.setImageURI(Uri.parse(data.imageUrl))
        }
    }

    /**
     * @author dingtao
     * @date 2019/1/3 9:23 AM
     * banner回调接口
     */
    internal inner class MyBanner : DataCall<List<Banner>> {

        override fun success(data: List<Banner>?, args:List<Any>) {
            mBanner!!.setIndicatorVisible(false)
            mBanner!!.setPages(data,  { BannerViewHolder() })
            mBanner!!.start()
        }

        override fun fail(e: ApiException, args:List<Any>) {

        }
    }

    /**
     * @author dingtao
     * @date 2019/1/3 9:23 AM
     * banner回调接口
     */
    internal inner class HomeCall : DataCall<HomeList> {

        override fun success(data: HomeList?, args:List<Any>) {
            //添加列表并刷新
            mHotAdapter?.addAll(data!!.rxxp.commodityList)
            mFashionAdapter?.addAll(data!!.mlss.commodityList)
            mLifeAdapter?.addAll(data!!.pzsh.commodityList)
            mHotAdapter?.notifyDataSetChanged()
            mFashionAdapter?.notifyDataSetChanged()
            mLifeAdapter?.notifyDataSetChanged()
        }

        override fun fail(e: ApiException, args:List<Any>) {

        }
    }

}
