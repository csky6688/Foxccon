package com.drkj.foxconn.mvp.presenter

import android.text.TextUtils
import com.drkj.foxconn.db.DbController
import com.drkj.foxconn.mvp.BasePresenter
import com.drkj.foxconn.mvp.view.IFeedbackView

/**
 * Created by VeronicaRen on 2018/3/9 in Kotlin
 */
class FeedbackPresenter : BasePresenter<IFeedbackView>() {

    fun createFeedback() {
        rootView!!.onCreateFeedback()
    }

    fun deployFeedback(feedbackId: String) {
        val feedbackBean = DbController.getInstance().queryFeedbackById(feedbackId)
        rootView!!.onDeployFeedback(feedbackBean)
    }

    fun queryFeedback(nfcCode: String) {
        val location = StringBuilder()

        if (!TextUtils.isEmpty(nfcCode)) {
            if (nfcCode.contains("XJ")) {
                val bean = DbController.getInstance().queryRegionByNfcCode(nfcCode)

                if (!TextUtils.isEmpty(bean.parentId)) {
                    var tempParentId: String = bean.parentId
                    while (true) {
                        val tempBean = DbController.getInstance().queryRegionInfoById(tempParentId)
                        if (!TextUtils.isEmpty(tempBean!!.parentId)) {
                            location.append(tempBean.name)
                            tempParentId = tempBean.parentId
                        } else {
                            break
                        }
                    }
                    location.append(bean.name)
                    rootView!!.onNfcCodeReceive(location.toString(), bean.code, nfcCode)
                } else {
                    rootView!!.onNoNfcCode(nfcCode)
                }
            } else {
                val newCode = "XJ${nfcCode.trim().substring(0, 6)}"
                val bean = DbController.getInstance().queryRegionByNfcCode(newCode)

                if (!TextUtils.isEmpty(bean.parentId)) {
                    var tempParentId: String = bean.parentId
                    while (true) {
                        val tempBean = DbController.getInstance().queryRegionInfoById(tempParentId)
                        if (!TextUtils.isEmpty(tempBean!!.parentId)) {
                            location.append(tempBean.name)
                            tempParentId = tempBean.parentId
                        } else {
                            break
                        }
                    }
                    location.append(bean.name)
                    rootView!!.onNfcCodeReceive(location.toString(), bean.code, nfcCode)
                } else {
                    rootView!!.onNoNfcCode(newCode)
                }
            }
        } else {
            rootView!!.onNfcReceiveFailed()
        }
    }
}