package com.hugh.kit.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

/**
 * Created by Hugh on 2020/3/2.
 *
 */
class DensityUtil {
    companion object{
        fun dp2px(dpValue: Float): Float {
            return 0.5f + dpValue * Resources.getSystem().displayMetrics.density
        }

        fun px2dp(pxValue: Float): Float {
            return pxValue / Resources.getSystem().displayMetrics.density
        }

        fun sp2px(context: Context, spVal: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    spVal, context.resources.displayMetrics)
        }

        fun px2sp(context: Context, pxVal: Float): Float {
            return pxVal / context.resources.displayMetrics.scaledDensity
        }
    }
}