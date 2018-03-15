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
        if (!TextUtils.isEmpty(nfcCode)) {
            val newCode = "XJ${nfcCode.trim().substring(0, 6)}"
            val bean = DbController.getInstance().queryFeedbackById(newCode)
            rootView!!.onNfcReceive(bean, newCode)
        } else {
            rootView!!.onNfcReceiveFailed()
        }
    }
}