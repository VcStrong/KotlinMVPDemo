package com.dingtao.common.core

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import java.util.ArrayList

/**
 * @author dingtao
 * @date 2019/1/10 16:39
 * qq:1940870847
 */
abstract class WDListAdpater<T, MH : WDListAdpater.Hodler> : BaseAdapter() {

    internal var mList: List<T> = ArrayList()

    abstract val layoutId: Int

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): T {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val mh: MH
        if (convertView == null) {
            convertView = View.inflate(parent.context, layoutId, null)
            mh = creatHolder(convertView)
            convertView!!.tag = mh
        } else {
            mh = convertView.tag as MH
        }
        bindItemView(mh, position)
        return convertView
    }

    protected abstract fun bindItemView(mh: MH, position: Int)

    abstract class Hodler(var itemView: View)

    abstract fun creatHolder(view: View?): MH

}
