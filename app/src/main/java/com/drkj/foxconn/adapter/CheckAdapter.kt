package com.drkj.foxconn.adapter

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.drkj.foxconn.R
import com.drkj.foxconn.bean.EquipmentResultBean
import com.drkj.foxconn.util.DateUtil

/**
 * 离线巡检列表适配器
 * Created by VeronicaRen on 2018/3/2. in Kotlin
 */
class CheckAdapter(context: Context, bean: EquipmentResultBean.DataBean) : BaseAdapter() {

    private val mContext = context

    private val mBean = bean

    interface OnContentChangedListener {
        fun onRangeChanged(hint: String)
    }

    private var mOnContentChangedListener: OnContentChangedListener? = null

    fun setOnContentChangedListener(listener: OnContentChangedListener) {
        this.mOnContentChangedListener = listener
    }

    fun getBean(): EquipmentResultBean.DataBean = mBean

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val itemView: View
        val max = mBean.equipmentAttributeList[position].max
        val min = mBean.equipmentAttributeList[position].min
        val type = mBean.equipmentAttributeList[position].type

        if (convertView == null) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_equipment_attr, null)
            holder = ViewHolder(itemView)
            itemView.tag = holder
        } else {
            itemView = convertView
            holder = itemView.tag as ViewHolder
        }

        holder.attrNameText.text = mBean.equipmentAttributeList[position].name
        holder.attrValueText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!TextUtils.isEmpty(charSequence)) {
                    val value = charSequence.toString().toDouble()
                    try {
                        if (max <= 0 && value > min) {
                            holder.attrHint.setBackgroundColor(Color.GREEN)
                        } else if (max >= 0) {
                            if (value < min || value > max) {
                                holder.attrHint.setBackgroundColor(Color.RED)
                            } else {
                                holder.attrHint.setBackgroundColor(Color.GREEN)
                            }
                        } else if (type != "0") {//累加
                            holder.attrHint.setBackgroundColor(Color.parseColor("#ffc000"))
                        } else {
                            holder.attrHint.setBackgroundColor(Color.parseColor("#ffc000"))
                        }
                        mBean.equipmentAttributeList[position].value = value
                    } catch (e: Exception) {

                    }
                } else {
                    holder.attrHint.setBackgroundColor(Color.parseColor("#ffc000"))
                }
            }
        })

        holder.attrValueText.setOnFocusChangeListener { view, hasFocus ->

            val range = "$min ~ $max"

            if (hasFocus) {
                if (type == "0") {//阈值
                    mOnContentChangedListener?.onRangeChanged(range)
                } else {//累加
                    mOnContentChangedListener?.onRangeChanged("")
                }
            } else {
                mOnContentChangedListener?.onRangeChanged("")
            }
        }

        if (!TextUtils.isEmpty(mBean.isCheck) && mBean.isCheck == "true") {
            holder.attrValueText.text = mBean.equipmentAttributeList[position].value.toString()
        }

        if (!TextUtils.isEmpty(mBean.equipmentAttributeList[position].createDate)) {
            mBean.equipmentAttributeList[position].createDate = DateUtil.getDate()
        } else {
            mBean.equipmentAttributeList[position].updateBy = DateUtil.getDate()
        }

        return itemView
    }

    override fun getItem(position: Int): Any = mBean.equipmentAttributeList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = mBean.equipmentAttributeList.size

    fun isAllNull(): Boolean {
        return !mBean.equipmentAttributeList.any { it.value > 0 }
    }

    inner class ViewHolder(itemView: View) {
        var attrNameText: TextView = itemView.findViewById(R.id.text_equipment_attr_name)
        var attrValueText: TextView = itemView.findViewById(R.id.text_equipment_attr_value)
        var attrHint: ImageView = itemView.findViewById(R.id.image_equipment_attr_hint)
    }
}