package com.drkj.foxconn.mvp

import android.content.res.Resources
import com.drkj.foxconn.App
import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * Created by VeronicaRen on 2018/2/23.
 */
abstract class BasePresenter<T : IBaseView> {

    private var reference: Reference<T>? = null

    var resources: Resources? = null

    var rootView: T? = null

    fun bindView(view: T) {
        reference = WeakReference<T>(view)
        rootView = reference!!.get()
        resources = App.getInstance().resources
    }

    open fun destroy() {
        reference?.clear()
    }
}