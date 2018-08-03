package com.seabig.moduledemo.common.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

/**
 * author :  YJZ
 * data  :  2018/3/19
 * desc : 相机人脸绘制的正方形
 */
class FaceView : View {

    private lateinit var mPaint: Paint
    private var mColor = "#ff0000"
    private var mFaces: ArrayList<RectF>? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mPaint = Paint()
        mPaint.color = Color.parseColor(mColor)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, context.resources.displayMetrics)
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mFaces?.let {
            for (face in it) {
                canvas.drawRect(face, mPaint)
            }
        }
    }

    fun setFaces(faces: ArrayList<RectF>) {
        this.mFaces = faces
        invalidate()
    }
}