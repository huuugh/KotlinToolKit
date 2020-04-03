package com.hugh.kit.utils

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

/**
 * Created by Hugh on 2020/3/31.
 *
 */
class JumpUtil {

    private lateinit var onFragmentAddedListener: FragmentAddedListener
    private val tag: String = com.hugh.kit.BuildConfig.APPLICATION_ID + JumpUtil::class.java.simpleName;
    private var resultFragment: BridgeFragment;

    constructor(manager: FragmentManager) {
        resultFragment = getBridgeFragment(manager)
    }

    private fun getBridgeFragment(manager: FragmentManager): BridgeFragment {
        var bridgeFragment = manager.findFragmentByTag(tag);
        if (bridgeFragment == null) {
            bridgeFragment = BridgeFragment();
            manager.beginTransaction().add(bridgeFragment, tag).commit();
        }
        return bridgeFragment as BridgeFragment
    }

    companion object {
        fun from(activity: FragmentActivity): JumpUtil {
            return JumpUtil(activity.supportFragmentManager);
        }

        fun from(fragment: Fragment): JumpUtil {
            return JumpUtil(fragment.childFragmentManager);
        }
    }

    fun startForResult(intent: Intent, resultCallBack: ResultCallBack) {
        resultFragment.startForResult(intent, resultCallBack)
    }

    fun startForResult(clazz: Class<*>, callBack: ResultCallBack) {
        var mActivity = resultFragment.activity;
        var intent = Intent(mActivity, clazz);
        startForResult(intent, callBack)
    }

    fun setOnFragmentAddedListener(onFragmentAddedListener: FragmentAddedListener) {
        this.onFragmentAddedListener = onFragmentAddedListener;
    }
}