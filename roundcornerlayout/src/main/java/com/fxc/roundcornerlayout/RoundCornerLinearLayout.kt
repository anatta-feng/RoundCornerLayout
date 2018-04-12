package com.fxc.roundcornerlayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import kotlin.math.min

/**
 * @author fxc
 * @date 2018/4/12
 */
class RoundCornerLinearLayout : LinearLayout, IRoundCornerLayout {
	override var isAttached: Boolean = false

	override var roundParams: IRoundCornerLayout.RoundParams = IRoundCornerLayout.RoundParams(this)
		set(value) {
			field = value
			invalidate()
		}

	constructor(context: Context?) : super(context)
	constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
		initRoundCornerAttrs(context, attrs)
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		isAttached = true
	}

	override fun onDetachedFromWindow() {
		super.onDetachedFromWindow()
		isAttached = false
	}

	init {
		setWillNotDraw(false)
	}

	override fun draw(canvas: Canvas) {
		viewBound.set(getViewBound())
		canvas.save()
		val bounds = obtainBounds()
		if (bounds.isEmpty) {
			super.draw(canvas)
			return
		}
		canvas.clipPath(bounds)
		super.draw(canvas)
		canvas.restore()
	}

	private val pathArc = RectF()
	private val viewBound: RectF = getViewBound()

	private fun getViewBound(): RectF {
		return RectF(paddingLeft.toFloat(), paddingTop.toFloat(), (width - paddingRight).toFloat(), (height - paddingBottom).toFloat())
	}

	private fun obtainBounds(): Path {
		val path = Path()
		if (roundParams.roundAsCircle) {
			val center = (min(width, height)).toFloat() / 2
			var radius = center
			if (roundParams.roundingBorderWidth != 0f) {
				radius -= roundParams.roundingBorderWidth
			}
			path.addCircle(center, center, radius, Path.Direction.CW)
			return path
		}
		val radius = roundParams.roundCornerRadius
		if (roundParams.isRoundTopLeft || roundParams.isRoundTopRight || roundParams.isRoundBottomLeft || roundParams.isRoundBottomRight) {
			if (roundParams.isRoundTopLeft) {
				pathArc.set(viewBound.left, viewBound.top, viewBound.left + 2 * radius, viewBound.top + 2 * radius)
				path.arcTo(pathArc, 180f, 90f)
			} else {
			}

			if (roundParams.isRoundTopRight) {
				path.lineTo(viewBound.right - radius, viewBound.top)
				pathArc.set(viewBound.right - 2 * radius, viewBound.top, viewBound.right, viewBound.top + 2 * radius)
				path.arcTo(pathArc, 270f, 90f)
			} else {
				path.lineTo(viewBound.right, viewBound.top)
			}

			if (roundParams.isRoundBottomRight) {
				path.lineTo(viewBound.right, viewBound.bottom - radius)
				pathArc.set(viewBound.right - 2 * radius, viewBound.bottom - 2 * radius, viewBound.right, viewBound.bottom)
				path.arcTo(pathArc, 0f, 90f)
			} else {
				path.lineTo(viewBound.right, viewBound.bottom)
			}

			if (roundParams.isRoundBottomLeft) {
				path.lineTo(viewBound.left + radius, viewBound.bottom)
				pathArc.set(viewBound.left, viewBound.bottom - 2 * radius, viewBound.left + 2 * radius, viewBound.bottom)
				path.arcTo(pathArc, 90f, 90f)
			} else {
				path.lineTo(viewBound.left, viewBound.bottom)
			}

			if (roundParams.isRoundTopLeft) {
				path.lineTo(viewBound.left, viewBound.top + radius)
			} else {
				path.lineTo(viewBound.left, viewBound.top)
			}
		}
		return path
	}


	override fun updateView() {
		invalidate()
	}
}