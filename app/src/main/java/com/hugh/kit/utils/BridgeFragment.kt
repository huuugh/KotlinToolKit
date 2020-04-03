package com.hugh.kit.utils

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.SparseArray

/**
 * Created by Hugh on 2020/3/31.
 *
 */
class BridgeFragment: Fragment() {
    private var mCallBacks:SparseArray<ResultCallBack> = SparseArray<ResultCallBack>();
    private var uniqueCode = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true;
    }

    @Synchronized fun startForResult(intent: Intent,callBack: ResultCallBack){
        uniqueCode++;
        mCallBacks.put(uniqueCode,callBack)
        startActivityForResult(intent,uniqueCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var callBack = mCallBacks.get(requestCode)
        if (callBack != null){
            callBack.onActivityResult(resultCode,data);
        }
    }
}