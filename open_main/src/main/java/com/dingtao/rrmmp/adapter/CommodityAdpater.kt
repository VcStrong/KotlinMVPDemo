package com.dingtao.rrmmp.adapter

import android.content.Context
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dingtao.rrmmp.R
import com.dingtao.common.bean.shop.Commodity
import com.facebook.drawee.view.SimpleDraweeView

import java.util.ArrayList

/**
 * @author dingtao
 * @date 2019/1/2 15:52
 * qq:1940870847
 */
class CommodityAdpater(internal var context: Context, private val type: Int) : RecyclerView.Adapter<CommodityAdpater.MyHolder>() {
    private val list = ArrayList<Commodity>()

    fun addAll(list: List<Commodity>?) {
        if (list != null) {
            this.list.addAll(list)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyHolder {
        if (type == HOT_TYPE) {
            val view = View.inflate(viewGroup.context, R.layout.hot_item, null)
            return MyHolder(view)
        } else {
            val view = View.inflate(viewGroup.context, R.layout.fashion_item, null)
            return MyHolder(view)
        }
    }

    override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
        val commodity = list[position]
        myHolder.image.setImageURI(Uri.parse(commodity.masterPic))
        myHolder.price.text = commodity.price.toString() + ""
        myHolder.text.text = commodity.commodityName

        myHolder.itemView.setOnClickListener {
            //获取分类id
            //                intent.putExtras("id",)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: SimpleDraweeView
        var text: TextView
        var price: TextView

        init {
            image = itemView.findViewById(R.id.image)
            text = itemView.findViewById(R.id.text)
            price = itemView.findViewById(R.id.price)
        }
    }

    companion object {
        val HOT_TYPE = 0
        val FASHION_TYPE = 1
    }
}
