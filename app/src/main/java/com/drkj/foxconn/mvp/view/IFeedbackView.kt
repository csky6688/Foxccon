package com.drkj.foxconn.mvp.view

import com.drkj.foxconn.bean.FeedbackBean
import com.drkj.foxconn.mvp.IBaseView

/**
 * Created by VeronicaRen on 2018/3/9 in Kotlin
 */
interface IFeedbackView : IBaseView {

    fun onCreateFeedback()

    fun onDeployFeedback(bean: FeedbackBean)

    fun onNfcCodeReceive(location: String, locationCode: String, nfcCode: String)

    fun onNoNfcCode(nfcCode: String)

    fun onNfcReceiveFailed()
}