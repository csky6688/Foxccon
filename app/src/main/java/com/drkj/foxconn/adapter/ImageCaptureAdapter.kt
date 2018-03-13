package com.drkj.foxconn.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.drkj.foxconn.R
import java.io.File

/**
 *
 * Created by VeronicaRen on 2018/2/28.
 */
class ImageCaptureAdapter(context: Context) : RecyclerView.Adapter<ImageCaptureAdapter.ViewHolder>() {

    private var mImgList = ArrayList<File>()

    private val mContext = context

    private val maxCount = 4//最多能装几张图片

    interface OnItemClickListener {

        fun onReceive(msg: String)

        fun onAddClick()
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun addPic(file: File) {
        mImgList.add(file)
        notifyItemChanged(mImgList.size - 1)
    }

    fun deletePic(position: Int) {
        notifyItemRemoved(position)
        mImgList.removeAt(position)
    }

    fun getAllData(): ArrayList<File> = mImgList

    private fun deletePic(file: File) {
        notifyItemRemoved(mImgList.indexOf(file))
        mImgList.remove(file)
    }

    fun setImgList(imgList: ArrayList<File>) {
        mImgList = imgList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_add_pic, parent, false))

    override fun getItemCount(): Int = mImgList.size + 1

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (mImgList.isEmpty()) {
            holder!!.itemPic.setOnClickListener { onItemClickListener?.onAddClick() }
            holder.itemDelete.visibility = View.GONE
        } else if (mImgList.isNotEmpty() && position == mImgList.size) {
            holder!!.itemPic.setOnClickListener {
                if (mImgList.size >= 4) {
                    onItemClickListener?.onReceive("最多只能储存${maxCount}张照片")
                } else {
                    onItemClickListener?.onAddClick()
                }
            }
            holder.itemDelete.visibility = View.GONE
        } else if (mImgList.isNotEmpty() && position - 1 < mImgList.size) {
            holder!!.itemDelete.visibility = View.VISIBLE
            Glide.with(mContext)
                    .load(mImgList[position])
                    .into(holder.itemPic)
            holder.itemView.tag = mImgList[position]
            holder.itemDelete.setOnClickListener {
                deletePic(holder.itemView.tag as File)
            }
        }
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val itemPic: ImageView = itemView!!.findViewById(R.id.item_pic)
        val itemDelete: ImageView = itemView!!.findViewById(R.id.item_pic_delete)
    }
}