package com.fxc.roundcornerlayout

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

/**
 * @author fxc
 * @date 2018/4/12
 */
interface IRoundCornerLayout {

	var isAttached: Boolean

	var roundParams: RoundParams
	fun initRoundCornerAttrs(ctx: Context, attr: AttributeSet) {
		val array = ctx.obtainStyledAttributes(attr, R.styleable.RoundCornerLayout)
		val count = array.indexCount
		(0 until count)
				.map { array.getIndex(it) }
				.forEach {
					when (it) {
						R.styleable.RoundCornerLayout_roundAsCircle -> roundParams.roundAsCircle = array.getBoolean(it, false)
						R.styleable.RoundCornerLayout_roundedCornerRadius -> roundParams.roundCornerRadius = array.getDimension(it, 0f)
						R.styleable.RoundCornerLayout_roundTopLeft -> roundParams.isRoundTopLeft = array.getBoolean(it, false)
						R.styleable.RoundCornerLayout_roundTopRight -> roundParams.isRoundTopRight = array.getBoolean(it, false)
						R.styleable.RoundCornerLayout_roundBottomLeft -> roundParams.isRoundBottomLeft = array.getBoolean(it, false)
						R.styleable.RoundCornerLayout_roundBottomRight -> roundParams.isRoundBottomRight = array.getBoolean(it, false)
						R.styleable.RoundCornerLayout_roundingBorderWidth -> roundParams.roundingBorderWidth = array.getDimension(it, 0f)
						R.styleable.RoundCornerLayout_roundingBorderColor -> roundParams.roundingBorderColor = array.getColor(it, 0)
					}
				}

		array.recycle()
	}

	fun setRoundBounds(canvas: Canvas) {
		canvas.save()
		canvas.restore()
	}

	fun updateView()


	class RoundParams(val view: IRoundCornerLayout) {
		var roundAsCircle: Boolean = false
			set(value) {
				field = value
				updateView()
			}

		var roundCornerRadius: Float = 0f
			set(value) {
				field = value
				updateView()
			}
		var isRoundTopLeft: Boolean = false
			set(value) {
				field = value
				updateView()
			}
		var isRoundTopRight: Boolean = false
			set(value) {
				field = value
				updateView()
			}
		var isRoundBottomLeft: Boolean = false
			set(value) {
				field = value
				updateView()
			}
		var isRoundBottomRight: Boolean = false
			set(value) {
				field = value
				updateView()
			}
		var roundingBorderWidth: Float = 0f
			set(value) {
				field = value
				updateView()
			}
		var roundingBorderColor: Int = 0
			set(value) {
				field = value
				updateView()
			}

		private fun updateView() {
			if (view.isAttached) {
				view.updateView()
			}
		}
	}
}