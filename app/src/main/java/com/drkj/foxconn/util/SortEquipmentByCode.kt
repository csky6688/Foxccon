package com.drkj.foxconn.util

import com.drkj.foxconn.bean.EquipmentResultBean

/**
 * 按NfcCode对设备排序
 * Created by VeronicaRen on 2018/3/28 in Kotlin
 */
class SortEquipmentByCode : Comparator<EquipmentResultBean.DataBean> {

    override fun compare(o1: EquipmentResultBean.DataBean?, o2: EquipmentResultBean.DataBean?): Int {
        val code1 = o1!!.nfcCode!!.substring(2, 8).toInt()
        val code2 = o2!!.nfcCode!!.substring(2, 8).toInt()

        return if (code1 > code2) {
            1
        } else {
            -1
        }
    }
}