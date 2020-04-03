package com.hugh.kit.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hugh.kit.utils.DensityUtil

/**
 * Created by Hugh on 2020/2/28.
 *
 */
class CircleProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mCenterX = 0;
    private var mCenterY = 0;
    private var transferMatrix: Matrix = Matrix();
    private var mProgressPaint:Paint = Paint();
    private var mNumberPaint:Paint = Paint();
    private var mTextPaint:Paint = Paint();
    private var mCirclePaint:Paint = Paint();
    private var mProgressBgPaint:Paint = Paint();
    var mCurrentPercent = 0;
    val mDesc = "已完成率";
    val muint = "%";
    private val colors = intArrayOf(Color.parseColor("#FF569AF7"), Color.parseColor("#FF5798F7"), Color.parseColor("#FF5A76F1"), Color.parseColor("#FF5C59EB"), Color.parseColor("#FF5C59EB"), Color.parseColor("#FF5C59EB"));

    init {
        mProgressPaint.isAntiAlias = true;
        mProgressPaint.color = Color.parseColor("#3092ea");
        mProgressPaint.style = Paint.Style.STROKE;
        mProgressPaint.strokeWidth = DensityUtil.dp2px(12f);

        mNumberPaint.isAntiAlias = true;
        mNumberPaint.color = Color.parseColor("#3092ea");
        mNumberPaint.textSize = DensityUtil.sp2px(context,60f)
        mNumberPaint.style = Paint.Style.FILL;
        mNumberPaint.isFakeBoldText = true;
        mNumberPaint.isSubpixelText = true;

        mTextPaint.isAntiAlias = true;
        mTextPaint.color = Color.parseColor("#3092ea");
        mTextPaint.textSize = DensityUtil.sp2px(context,18f)
        mTextPaint.style = Paint.Style.FILL;
        mTextPaint.isSubpixelText = true;

        mCirclePaint.isAntiAlias = true;
        mCirclePaint.color = colors[5];
        mCirclePaint.textSize = DensityUtil.sp2px(context,18f)
        mCirclePaint.style = Paint.Style.FILL;
        mCirclePaint.isSubpixelText = true;
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val rectF = RectF(DensityUtil.px2dp(10f), DensityUtil.px2dp(10f), (getWidth() - DensityUtil.dp2px(10f)).toFloat(), (getHeight() - DensityUtil.dp2px(10f)).toFloat());
        mCenterX = width shr 1
        mCenterY = height shr 1
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = 0;
        var heightSize = 0;
        var widthMode = MeasureSpec.getMode(widthMeasureSpec);
        var heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY){
            widthSize = MeasureSpec.getSize(widthMeasureSpec);
        }else if (widthMode == MeasureSpec.AT_MOST){
            widthSize = DensityUtil.dp2px(50f).toInt();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            heightSize = DensityUtil.dp2px(275f).toInt();
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val percent = mCurrentPercent.toString();

    }
}