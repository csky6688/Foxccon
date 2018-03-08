package com.drkj.foxconn.mvp

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * Created by VeronicaRen on 2018/2/23.
 */
abstract class BasePresenter<T : IBaseView> {

    private var reference: Reference<T>? = null

    var rootView: T? = null

    fun bindView(view: T) {
        reference = WeakReference<T>(view)
        rootView = reference!!.get()
    }

    open fun destory() {
        reference?.clear()
    }
}