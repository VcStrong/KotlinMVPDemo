package com.dingtao.rrmmp.adapter

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import com.dingtao.rrmmp.R
import com.dingtao.common.core.WDActivity
import com.dingtao.common.util.UIUtils
import com.facebook.drawee.view.SimpleDraweeView

import java.util.ArrayList

/**
 * @author dingtao
 * @date 2019/1/3 23:24
 * qq:1940870847
 */
class ImageAdapter : RecyclerView.Adapter<ImageAdapter.MyHodler>() {

    private val mList = ArrayList<Any>()
    private var sign: Int = 0//0:普通点击，1自定义

    val list: List<Any>
        get() = mList

    fun addAll(list: List<Any>) {
        mList.addAll(list)
    }

    fun addStringListAll(list: List<String>) {
        mList.addAll(list)
    }

    fun setSign(sign: Int) {
        this.sign = sign
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHodler {
        val view = View.inflate(viewGroup.context, R.layout.circle_image_item, null)
        return MyHodler(view)
    }

    override fun onBindViewHolder(myHodler: MyHodler, position: Int) {
        if (mList[position] is String) {
            val imageUrl = mList[position] as String
            if (imageUrl.contains("http:")) {//加载http
                myHodler.image.setImageURI(Uri.parse(imageUrl))
            } else {//加载sd卡
                val uri = Uri.parse("file://$imageUrl")
                myHodler.image.setImageURI(uri)
            }
        } else {//加载资源文件
            val id = mList[position] as Int
            val uri = Uri.parse("res://com.dingtao.rrmmp/$id")
            myHodler.image.setImageURI(uri)
        }

        myHodler.itemView.setOnClickListener {
            if (sign == 1) {//自定义点击
                if (position == 0) {
                    val intent = Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    WDActivity.foregroundActivity!!.startActivityForResult(intent, WDActivity.PHOTO)
                } else {
                    UIUtils.showToastSafe("点击了图片")
                }
            } else {
                UIUtils.showToastSafe("点击了图片")
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun clear() {
        mList.clear()
    }

    fun add(image: Any?) {
        if (image != null) {
            mList.add(image)
        }
    }

    inner class MyHodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: SimpleDraweeView

        init {
            image = itemView.findViewById(R.id.circle_image)
        }
    }
}
