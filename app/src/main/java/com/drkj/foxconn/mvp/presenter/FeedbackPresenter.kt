package com.drkj.foxconn.mvp.presenter

import com.drkj.foxconn.db.DbController
import com.drkj.foxconn.mvp.BasePresenter
import com.drkj.foxconn.mvp.view.IFeedbackView

/**
 * Created by VeronicaRen on 2018/3/9 in Kotlin
 */
class FeedbackPresenter : BasePresenter<IFeedbackView>() {

    fun createFeedback() {

    }

    fun deployFeedback(feedbackId: String) {
        val feedbackBean = DbController.getInstance().queryFeedbackById(feedbackId)
        rootView!!.onDeployFeedback(feedbackBean)
    }
}