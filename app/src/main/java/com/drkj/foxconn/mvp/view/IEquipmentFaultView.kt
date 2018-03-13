package com.drkj.foxconn.mvp.view

import com.drkj.foxconn.bean.EquipmentFaultBean
import com.drkj.foxconn.mvp.IBaseView

/**
 * Created by VeronicaRen on 2018/3/12 in Kotlin
 */
interface IEquipmentFaultView : IBaseView {

    fun onDeployFault(bean: EquipmentFaultBean)

    fun onFaultCreate()
}