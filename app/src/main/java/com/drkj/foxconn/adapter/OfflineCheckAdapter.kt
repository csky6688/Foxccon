package com.drkj.foxconn.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.drkj.foxconn.R
import com.drkj.foxconn.bean.EquipmentResultBean
import com.drkj.foxconn.db.DbController

/**
 * 离线巡检list适配器
 * Created by VeronicaRen on 2018/2/27.
 */
class OfflineCheckAdapter(context: Context, dataBeans: List<EquipmentResultBean.DataBean>) : BaseAdapter() {

    private val mContext = context

    private val mDataBeans = dataBeans

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder? = null
        val view: View
        if (holder == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_equipment_list, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView!!
            holder = convertView.tag as ViewHolder
        }

        when (position % 3) {//设置item背景色，三个一循环
            0 -> holder.layout.setBackgroundResource(R.drawable.ic_orange_bg)
            1 -> holder.layout.setBackgroundResource(R.drawable.ic_green_bg)
            2 -> holder.layout.setBackgroundResource(R.drawable.ic_blue_bg)
        }

        holder.equipmentNameText.text = mDataBeans[position].name
        holder.equipmentDefaultCode.text = mDataBeans[position].code
        holder.equipmentNfcCode.text = mDataBeans[position].nfcCode

        val dataBean0 = DbController.getInstance().queryRegionInfoById(mDataBeans[position].buildingId)
        holder.buildingNameText.text = dataBean0.name

        val dataBean1 = DbController.getInstance().queryRegionInfoById(mDataBeans[position].storeyId)
        val dataBean2 = DbController.getInstance().queryRegionInfoById(mDataBeans[position].roomId)
        val location = dataBean0.name + dataBean1.name + dataBean2.name
        holder.equipmentLocationText.text = location



        if (!TextUtils.isEmpty(mDataBeans[position].isCheck) && mDataBeans[position].isCheck == "true") {
            holder.checkImg.visibility = View.VISIBLE
        } else {
            holder.checkImg.visibility = View.INVISIBLE
        }

        return view
    }

    override fun getItem(p0: Int): Any = Any()

    override fun getItemId(p0: Int): Long = 0

    override fun getCount(): Int = mDataBeans.size

    inner class ViewHolder(itemView: View) {
        var equipmentLocationText: TextView = itemView.findViewById(R.id.equipment_offline_item_tv_location)
        var equipmentNameText: TextView = itemView.findViewById(R.id.equipment_offline_item_tv_name)
        var buildingNameText: TextView = itemView.findViewById(R.id.text_building_name)
        var layout: LinearLayout = itemView.findViewById(R.id.equipment_offline_layout_item)
        var equipmentDefaultCode: TextView = itemView.findViewById(R.id.text_equipment_default_code)
        var equipmentNfcCode: TextView = itemView.findViewById(R.id.text_equipment_nfc_code)
        var checkImg: ImageView = itemView.findViewById(R.id.item_check_img)
    }
}