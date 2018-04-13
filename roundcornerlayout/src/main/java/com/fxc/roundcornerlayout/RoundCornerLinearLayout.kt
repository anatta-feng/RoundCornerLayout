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
	override val boundPath: Path = Path()

	override fun makeVpCanDraw() {
		setWillNotDraw(false)
	}

	override val pathArc: RectF = RectF()
	override val viewBound: RectF = RectF()
		get() {
			field.set(paddingLeft.toFloat(), paddingTop.toFloat(), (width - paddingRight).toFloat(), (height - paddingBottom).toFloat())
			return field
		}


	override var isAttached: Boolean = false

	override var roundParams: IRoundCornerLayout.RoundParams = IRoundCornerLayout.RoundParams(this)
		set(value) {
			field = value
			invalidate()
		}

	override fun getMinSize(): Float {
		return (min(width, height)).toFloat()
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

	override fun draw(canvas: Canvas) {
		beforeDraw(canvas)
		super.draw(canvas)
		afterDraw(canvas)
	}

	override fun updateView() {
		invalidate()
	}
}