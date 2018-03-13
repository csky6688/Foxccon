package com.drkj.foxconn.mvp.presenter

import com.drkj.foxconn.db.DbController
import com.drkj.foxconn.mvp.BasePresenter
import com.drkj.foxconn.mvp.view.IEquipmentFaultView

/**
 * Created by VeronicaRen on 2018/3/12 in Kotlin
 */
class EquipmentFaultPresenter : BasePresenter<IEquipmentFaultView>() {

    fun deployData(id: String) {
        val equipmentFaultBean = DbController.getInstance().queryEquipmentFaultById(id)
        rootView!!.onDeployFault(equipmentFaultBean)
    }

    fun createData() {
        rootView!!.onFaultCreate()
    }
}