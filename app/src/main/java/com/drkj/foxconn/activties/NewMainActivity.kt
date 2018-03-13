package com.drkj.foxconn.activties

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.*
import com.drkj.foxconn.R
import com.drkj.foxconn.fragments.DataSynchronizationFragment
import com.drkj.foxconn.fragments.EquipmentFaultFragment
import com.drkj.foxconn.fragments.FeedbackFragment
import com.drkj.foxconn.fragments.OfflineCheckFragment
import com.drkj.foxconn.mvp.presenter.MainPresenter
import com.drkj.foxconn.mvp.view.IMainView
import kotterknife.bindView

/**
 * Created by VeronicaRen on 2018/3/7 in Kotlin
 */
class NewMainActivity : BaseActivity(), IMainView, View.OnClickListener {

    public val FRAGMENT_SYNC = 0
    public val FRAGMENT_OFFLINE_CHECK = 1
    val FRAGMENT_FEEDBACK = 2
    val FRAGMENT_FAULT = 3

    private val presenter = MainPresenter()

    private val mConentView: FrameLayout by bindView(R.id.new_main_content)

    private val imageSync: ImageView by bindView(R.id.new_main_image_data_synchronization)

    private val imageOffline: ImageView by bindView(R.id.new_main_image_offline_check)

    private val imageFeedback: ImageView by bindView(R.id.new_main_image_scene_feedback)

    private val imageFault: ImageView by bindView(R.id.new_main_image_equipment_fault)

    private val tvTitle: TextView by bindView(R.id.new_main_tv_title)

    private val layoutTab: LinearLayout by bindView(R.id.new_main_tab)

    val fragmentList = ArrayList<Fragment>()

    private var currentFragment: Fragment? = null

    override fun setLayout(): Int = R.layout.activity_new_main

    override fun initView() {
        val dataSyncFragment = DataSynchronizationFragment()
        val offlineCheckFragment = OfflineCheckFragment()
        val feedbackFragment = FeedbackFragment()
        val equipmentFaultFragment = EquipmentFaultFragment()

        fragmentList.add(dataSyncFragment)
        fragmentList.add(offlineCheckFragment)
        fragmentList.add(feedbackFragment)
        fragmentList.add(equipmentFaultFragment)

        imageSync.setOnClickListener(this)
        imageOffline.setOnClickListener(this)
        imageFeedback.setOnClickListener(this)
        imageFault.setOnClickListener(this)

        switchFragment(fragmentList[FRAGMENT_SYNC]).commit()//默认加载第一个界面
        tvTitle.text = "数据同步"
        hideTab()
    }

    override fun onClick(view: View?) {
        resetButtons()
        when (view!!.id) {
            R.id.new_main_image_data_synchronization -> {
                imageSync.setImageResource(R.drawable.ic_data_synchronization_selected)
                switchFragment(fragmentList[FRAGMENT_SYNC]).commit()
                tvTitle.text = "数据同步"
            }
            R.id.new_main_image_offline_check -> {
                imageOffline.setImageResource(R.drawable.ic_offline_check_selected)
                switchFragment(fragmentList[FRAGMENT_OFFLINE_CHECK]).commit()
                tvTitle.text = "巡检作业"
            }
            R.id.new_main_image_scene_feedback -> {
                imageFeedback.setImageResource(R.drawable.ic_scene_feedback_selected)
                switchFragment(fragmentList[FRAGMENT_FEEDBACK]).commit()
                tvTitle.text = "现场反馈"
            }
            R.id.new_main_image_equipment_fault -> {
                imageFault.setImageResource(R.drawable.ic_equipment_fault_selected)
                switchFragment(fragmentList[FRAGMENT_FAULT]).commit()
                tvTitle.text = "设备故障"
            }
        }
    }

    private fun resetButtons() {
        imageSync.setImageResource(R.drawable.ic_data_synchronization_unselected)
        imageOffline.setImageResource(R.drawable.ic_offline_check_unselected)
        imageFeedback.setImageResource(R.drawable.ic_scene_feedback_unselected)
        imageFault.setImageResource(R.drawable.ic_equipment_fault_unselected)
    }

//    fun switchFragment(targetFragment: Fragment): FragmentTransaction {
//        val transaction = supportFragmentManager.beginTransaction()
//
//        transaction.replace(R.id.new_main_content, targetFragment)
//        currentFragment = targetFragment
////        if (!targetFragment.isAdded) {
////            if (currentFragment != null) {
////                transaction.hide(currentFragment)
////            }
////            transaction.add(R.id.new_main_content, targetFragment)
////        } else {
////            transaction.hide(currentFragment).show(targetFragment)
////        }
////        currentFragment = targetFragment
//        return transaction
//    }

    fun switchFragment(targetFragment: Fragment): FragmentTransaction {
        val transaction = supportFragmentManager.beginTransaction()

        if (!targetFragment.isAdded) {
            if (currentFragment != null) {
                transaction.hide(currentFragment)
            }
            transaction.add(R.id.new_main_content, targetFragment)
        } else {
            transaction.hide(currentFragment).show(targetFragment)
        }
        currentFragment = targetFragment
        return transaction
    }

    fun hideTab() {
        runOnUiThread { layoutTab.visibility = View.GONE }
    }

    fun showTab() {
        runOnUiThread { layoutTab.visibility = View.VISIBLE }
        resetButtons()
        imageOffline.setImageResource(R.drawable.ic_offline_check_selected)
        switchFragment(fragmentList[FRAGMENT_OFFLINE_CHECK]).commit()
        tvTitle.text = "巡检作业"
    }

    fun getFragmentList(): List<Fragment> = fragmentList
}