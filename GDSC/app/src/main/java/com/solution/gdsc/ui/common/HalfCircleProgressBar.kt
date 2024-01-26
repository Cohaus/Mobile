package com.solution.gdsc.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.solution.gdsc.R
import kotlin.math.cos
import kotlin.math.sin


class HalfCircleProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val oval: RectF = RectF()

    private var progress: Float = 0f

    private val indicatorPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val indicatorStrokePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val checkMarkPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val checkMarkPath = Path()
    private val matrix = Matrix()

    init {
        // 진행 중인 부분의 색상 및 스트로크 설정
        progressPaint.color = context.getColor(R.color.green_300)
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeWidth = 47f // 스트로크의 너비 조절

        // 전체 반원의 배경 색상 및 스트로크 설정
        backgroundPaint.color = context.getColor(R.color.gray_middle)
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.strokeWidth = 47f // 스트로크의 너비 조절

        // Indicator 페인트 설정
        indicatorPaint.color = context.getColor(R.color.white)
        indicatorPaint.style = Paint.Style.FILL // 채우기 스타일

        // Indicator 스트로크 페인트 설정
        indicatorStrokePaint.color = context.getColor(R.color.green_300)
        indicatorStrokePaint.style = Paint.Style.STROKE // 스트로크 스타일
        indicatorStrokePaint.strokeWidth = 5f // 스트로크의 너비 조절

        // 체크 표시 페인트 설정
        checkMarkPaint.color = context.getColor(R.color.green_300)
        checkMarkPaint.style = Paint.Style.STROKE
        checkMarkPaint.strokeWidth = 5f
        checkMarkPaint.strokeCap = Paint.Cap.ROUND
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate() // 뷰를 다시 그리도록 갱신
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 뷰의 크기를 지정
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // View의 중심 좌표와 radius 계산
        val centerX = width / 2f
        val centerY = height.toFloat() // 전체 뷰의 높이만큼 사용
        val radius = centerY - backgroundPaint.strokeWidth / 2 // 공백 부분은 제외

        // oval을 설정할 때 strokeWidth 고려하여 크기 조정
        oval.set(centerX - radius, centerY - radius,
            centerX + radius, centerY + radius)

        // 회전 각도 설정 (90도)
        canvas.rotate(180f, centerX, centerY)

        // 전체 반원 그리기
        canvas.drawArc(oval, 0f, 180f, false, backgroundPaint)

        // 진행 중인 부분 그리기
        val sweepAngle = 180 * (progress / 100)


        canvas.drawArc(oval, 0f, sweepAngle, false, progressPaint)

        // 체크 표시 그리기
        if (progress > 0f && progress <= 90f) {
            drawIndicator(centerX, centerY, radius, sweepAngle, canvas)
        }
    }

    private fun drawIndicator(centerX: Float, centerY: Float, radius: Float, sweepAngle: Float, canvas: Canvas) {
        val indicatorX = centerX + radius * cos(Math.toRadians(sweepAngle.toDouble())).toFloat()
        val indicatorY = centerY + radius * sin(Math.toRadians(sweepAngle.toDouble())).toFloat()
        canvas.drawCircle(indicatorX, indicatorY, 31f, indicatorPaint)
        // Indicator 스트로크 그리기
        canvas.drawCircle(indicatorX, indicatorY, 31f, indicatorStrokePaint)
        drawCheckMark(indicatorX, indicatorY, canvas)
    }

    private fun drawCheckMark(indicatorX: Float, indicatorY: Float, canvas: Canvas) {
        val checkMarkSize = 23f
        checkMarkPath.moveTo(indicatorX - checkMarkSize / 2, indicatorY)
        checkMarkPath.lineTo(indicatorX, indicatorY + checkMarkSize / 2)
        checkMarkPath.lineTo(indicatorX + checkMarkSize / 2, indicatorY - checkMarkSize / 2)

        matrix.postRotate(-180f, indicatorX, indicatorY)
        checkMarkPath.transform(matrix)

        canvas.drawPath(checkMarkPath, checkMarkPaint)
    }
}