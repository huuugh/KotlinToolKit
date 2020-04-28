package com.hugh.kit.views

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup

/**
 * Created by Hugh on 2020/4/27.
 */
class RefreshLayout : ViewGroup {
    private var mTheLastPosition: Int = 0;
    private var mListener: OnRefreshListener? = null
    private var mStatus = STATUS.NORMAL
    private var mIsDragging: Boolean = false
    private var mOffsetTop = 0
    private var mHeaderView: View? = null
    private var mHeaderHeight = 0
    private var mDistanceToTriggerSync = -1
    private var mDownMotionY = 0f
    private var mLastMotionY = 0f
    private var mActivePointerId = 0
    private var mTouchSlop = 0;

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private enum class STATUS {
        NORMAL, LOOSEN, REFRESHING
    }


    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop;
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight
        if (childCount == 0) {
            return
        }
        val child0 = getChildAt(0)
        val childLeft = paddingLeft
        val childRight = measuredWidth + childLeft
        val childTop = mOffsetTop + paddingTop
        child0.layout(childLeft, childTop - child0.measuredHeight, childRight, child0.measuredHeight)

        val child1 = getChildAt(1);
        child1.layout(childLeft, childTop, childRight, child0.measuredHeight + child1.measuredHeight)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        check(childCount > 1) { "SwipeRefreshLayout must have the headerView and contentView" }
        check(!(childCount > 2 && !isInEditMode)) { "SwipeRefreshLayout can only host two children" }
        if (mHeaderView == null) {
            mHeaderView = getChildAt(0)
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec)
            mHeaderHeight = mHeaderView!!.height
            mDistanceToTriggerSync = mHeaderHeight
        }
        getChildAt(1).measure(
                MeasureSpec.makeMeasureSpec(measuredWidth
                        - paddingLeft - paddingRight,
                        MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(measuredHeight
                        - paddingTop - paddingBottom,
                        MeasureSpec.EXACTLY))
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        if (action == MotionEvent.ACTION_DOWN) {
            mLastMotionY = event.y
            mDownMotionY = mLastMotionY
            val index = event.actionIndex
            mActivePointerId = event.getPointerId(index)
            mIsDragging = false;
        } else if (action == MotionEvent.ACTION_POINTER_DOWN) {
        } else if (action == MotionEvent.ACTION_MOVE) {
            var moveY = event.y
            mOffsetTop = (moveY - mDownMotionY).toInt();
//            Log.e("Refresh", "moveY:$moveY  mOffsetTop:$mOffsetTop")
            var height = getChildAt(0).measuredHeight
            Log.e("Refresh", "height:$height")
            if (mOffsetTop in 0..height) {
                requestLayout()
                mTheLastPosition = mOffsetTop
            }
        } else if (action == MotionEvent.ACTION_UP) {
            returnToInitial();
        }
        return true
    }

    private fun returnToInitial() {
        val animator = ValueAnimator.ofInt(mTheLastPosition, 0);
        animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener {
            var value = it.animatedValue;
            mOffsetTop = value as Int;
            requestLayout()
        })
        animator.duration = 500;
        animator.start()
    }

    interface OnRefreshListener {
        fun onNormal()

        fun onLoose()

        fun onRefresh()
    }

    fun setOnRefreshListener(mListener: OnRefreshListener) {
        this.mListener = mListener;
    }
}