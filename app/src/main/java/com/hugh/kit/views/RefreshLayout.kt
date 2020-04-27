package com.hugh.kit.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup

/**
 * Created by Hugh on 2020/4/27.
 */
class RefreshLayout : ViewGroup {
    private var mIsDragging: Boolean = false
    private val mOffsetTop = 0
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
        val childBottom = measuredHeight + childTop
        child0.layout(childLeft, childTop, childRight, childBottom)
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
            val pointerIndex = event.findPointerIndex(mActivePointerId)
            //	Returns either the index of the pointer (for use with getX(int) et al.),
            //	or -1 if there is no data available for that pointer identifier.
            if (pointerIndex == -1) {
                return false;
            }
            var moveY = event.getY(pointerIndex)
            var yDiff = y - mDownMotionY
            if (!mIsDragging && yDiff > mTouchSlop) {
                mIsDragging = true;
            }
            if (mIsDragging){
                if (yDiff>mDistanceToTriggerSync)
            }
        } else if (action == MotionEvent.ACTION_UP) {
        }
        return true
    }

    interface OnRefreshListener {
        fun onNormal()
        fun onLoose()
        fun onRefresh()
    }
}