package com.drkj.foxconn.mvp.presenter

import android.text.TextUtils
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

    fun queryEquipmentFault(nfcCode: String) {
        if (!TextUtils.isEmpty(nfcCode)) {
            if (nfcCode.contains("XJ")) {
                val equipmentResultBean = DbController.getInstance().queryEquipmentByNfcCode(nfcCode)

                val location = StringBuilder()

                if (!TextUtils.isEmpty(equipmentResultBean.buildingId)) {
                    location.append(DbController.getInstance().queryRegionInfoById(equipmentResultBean.buildingId).name)
                }

                if (!TextUtils.isEmpty(equipmentResultBean.storeyId)) {
                    location.append(DbController.getInstance().queryRegionInfoById(equipmentResultBean.storeyId).name)
                }

                if (!TextUtils.isEmpty(equipmentResultBean.roomId)) {
                    location.append(DbController.getInstance().queryRegionInfoById(equipmentResultBean.roomId).name)
                }

                rootView!!.onNfcReceive(equipmentResultBean, location.toString(), nfcCode)
            } else {
                val newCode = "XJ${nfcCode.trim().substring(0, 6)}"

//                val newCode = nfcCode

                val equipmentResultBean = DbController.getInstance().queryEquipmentByNfcCode(newCode)

                val location = StringBuilder()

                if (!TextUtils.isEmpty(equipmentResultBean.buildingId)) {
                    location.append(DbController.getInstance().queryRegionInfoById(equipmentResultBean.buildingId).name)
                }

                if (!TextUtils.isEmpty(equipmentResultBean.storeyId)) {
                    location.append(DbController.getInstance().queryRegionInfoById(equipmentResultBean.storeyId).name)
                }

                if (!TextUtils.isEmpty(equipmentResultBean.roomId)) {
                    location.append(DbController.getInstance().queryRegionInfoById(equipmentResultBean.roomId).name)
                }

                rootView!!.onNfcReceive(equipmentResultBean, location.toString(), newCode)
            }
        } else {
            rootView!!.onNfcReceiveFailed()
        }
    }
}