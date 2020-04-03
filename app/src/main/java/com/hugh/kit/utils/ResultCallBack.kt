package com.hugh.kit.utils

import android.content.Intent

/**
 * Created by Hugh on 2020/3/31.
 *
 */
interface ResultCallBack {
    fun onActivityResult(resultCode:Int, data: Intent?);
}