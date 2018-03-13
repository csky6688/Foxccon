package com.drkj.foxconn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.drkj.foxconn.R
import com.drkj.foxconn.bean.FeedbackBean

/**
 * 反馈列表适配器
 * Created by VeronicaRen on 2018/3/9 in Kotlin
 */
class FeedbackAdapter(context: Context, beanList: List<FeedbackBean>) : BaseAdapter() {

    private val mBeanList = beanList

    private val mContext = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currentBean = mBeanList[position]

        val holder: ViewHolder
        val itemView: View

        if (convertView == null) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_feedback_list, null)
            holder = ViewHolder(itemView)
            itemView.tag = holder
        } else {
            itemView = convertView
            holder = itemView.tag as ViewHolder
        }

        if (currentBean.type == "0") {
            holder.tvTitle.text = "人为"
        } else {
            holder.tvTitle.text = "非人为"
        }

        holder.tvLocation.text = currentBean.regionName

        when (position % 3) {
            0 -> holder.bgLayout.setBackgroundResource(R.drawable.ic_orange_bg)
            1 -> holder.bgLayout.setBackgroundResource(R.drawable.ic_green_bg)
            2 -> holder.bgLayout.setBackgroundResource(R.drawable.ic_blue_bg)
        }

        return itemView
    }

    override fun getItem(position: Int): Any = mBeanList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = mBeanList.size

    inner class ViewHolder(itemView: View) {
        val tvTitle: TextView = itemView.findViewById(R.id.feedback_tv_name)
        val tvLocation: TextView = itemView.findViewById(R.id.feedback_tv_location)
        val bgLayout: LinearLayout = itemView.findViewById(R.id.feedback_layout_item)
    }
}