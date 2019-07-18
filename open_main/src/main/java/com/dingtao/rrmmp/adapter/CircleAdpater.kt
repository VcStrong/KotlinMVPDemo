package com.dingtao.rrmmp.adapter

import android.content.Context
import android.net.Uri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.dingtao.rrmmp.R
import com.dingtao.common.bean.Circle
import com.dingtao.common.util.DateUtils
import com.dingtao.common.util.StringUtils
import com.dingtao.rrmmp.presenter.GreatPresenter
import com.dingtao.common.util.recyclerview.SpacingItemDecoration
import com.facebook.drawee.view.SimpleDraweeView

import java.text.ParseException
import java.util.ArrayList
import java.util.Arrays
import java.util.Date

/**
 * @author dingtao
 * @date 2019/1/2 15:52
 * qq:1940870847
 */
class CircleAdpater(internal var context: Context) : RecyclerView.Adapter<CircleAdpater.MyHolder>() {
    private val list = ArrayList<Circle>()
    private val greatPresenter: GreatPresenter? = null

    private var greatListener: GreatListener? = null

    fun addAll(list: List<Circle>?) {
        if (list != null) {
            this.list.addAll(list)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyHolder {
        val view = View.inflate(viewGroup.context, R.layout.circle_item, null)
        return MyHolder(view)
    }

    override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
        val circle = list[position]
        myHolder.avatar.setImageURI(Uri.parse(circle.headPic))
        myHolder.nickname.text = circle.nickName
        try {
            myHolder.time.text = DateUtils.dateFormat(Date(circle.createTime), DateUtils.MINUTE_PATTERN)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        myHolder.text.text = circle.content

        if (StringUtils.isEmpty(circle.image)) {
            myHolder.gridView.visibility = View.GONE
        } else {
            myHolder.gridView.visibility = View.VISIBLE
            val images = circle.image.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

            //            int imageCount = (int)(Math.random()*9)+1;
            val imageCount = images.size

            val colNum: Int//列数
            if (imageCount == 1) {
                colNum = 1
            } else if (imageCount == 2 || imageCount == 4) {
                colNum = 2
            } else {
                colNum = 3
            }
            myHolder.imageAdapter.clear()//清空
            //            for (int i = 0; i <imageCount ; i++) {
            //                myHolder.imageAdapter.addAll(Arrays.asList(images));
            //            }
            myHolder.imageAdapter.addStringListAll(Arrays.asList(*images))
            myHolder.gridLayoutManager.spanCount = colNum//设置列数


            myHolder.imageAdapter.notifyDataSetChanged()
        }

        if (circle.whetherGreat == 1) {
            myHolder.priseImage.setImageResource(R.drawable.common_btn_prise_s)
        } else {
            myHolder.priseImage.setImageResource(R.drawable.common_btn_prise_n)
        }

        myHolder.priseText.text = circle.greatNum.toString() + ""
        myHolder.priseLayout.setOnClickListener {
            if (greatListener != null) {
                greatListener!!.great(position, circle)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun clear() {
        list.clear()
    }

    fun getItem(postion: Int): Circle {
        return list[postion]
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var avatar: SimpleDraweeView
        var nickname: TextView
        var time: TextView
        var text: TextView
        var gridView: RecyclerView
        var gridLayoutManager: GridLayoutManager
        var imageAdapter: ImageAdapter
        var priseLayout: LinearLayout
        var priseImage: ImageView
        var priseText: TextView

        init {
            avatar = itemView.findViewById(R.id.image)
            text = itemView.findViewById(R.id.text)
            nickname = itemView.findViewById(R.id.nickname)
            time = itemView.findViewById(R.id.time)
            gridView = itemView.findViewById(R.id.grid_view)
            imageAdapter = ImageAdapter()
            val space = context.resources.getDimensionPixelSize(R.dimen.dip_10)
            gridLayoutManager = GridLayoutManager(context, 3)
            gridView.addItemDecoration(SpacingItemDecoration(space))
            gridView.layoutManager = gridLayoutManager
            gridView.adapter = imageAdapter
            priseImage = itemView.findViewById(R.id.prise_image)
            priseText = itemView.findViewById(R.id.prise_count)
            priseLayout = itemView.findViewById(R.id.prise_layout)
        }//图片间距
    }

    fun setGreatListener(greatListener: GreatListener) {
        this.greatListener = greatListener
    }

    interface GreatListener {
        fun great(position: Int, circle: Circle)
    }
}
