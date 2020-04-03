package com.hugh.kit

import android.app.Activity
import android.app.Application

/**
 * Created by Hugh on 2020/4/3.
 *
 */
class App : Application() {
    var activityList = ArrayList<Activity>();

    fun getActivity(): Activity? {
        if (activityList.size>0){
            return activityList[0];
        }
        return null;
    }

    fun addActivity(activity: Activity){
        activityList.add(activity)
    }

    fun removeActivity(activity: Activity){
        activityList.remove(activity)
    }
}