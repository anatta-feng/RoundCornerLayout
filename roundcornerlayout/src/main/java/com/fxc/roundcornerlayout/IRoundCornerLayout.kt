package com.fxc.roundcornerlayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet

/**
 * @author fxc
 * @date 2018/4/12
 */
interface IRoundCornerLayout {

	var isAttached: Boolean
	val pathArc: RectF
	val viewBound: RectF
	val boundPath: Path

	var roundParams: RoundParams
	fun initRoundCornerAttrs(ctx: Context, attr: AttributeSet) {
		makeVpCanDraw()
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

	fun makeVpCanDraw()

	fun setRoundBounds(canvas: Canvas) {
		canvas.save()
		canvas.restore()
	}

	fun updateView()

	fun getMinSize(): Float

	private fun ifRoundAsCircle(path: Path): Boolean {
		if (roundParams.roundAsCircle) {
			val center = getMinSize() / 2
			var radius = center
			if (roundParams.roundingBorderWidth != 0f) {
				radius -= roundParams.roundingBorderWidth
			}
			path.addCircle(center, center, radius, Path.Direction.CW)
			return true
		}
		return false
	}

	private fun isAnyCornerHasRound(): Boolean {
		return roundParams.isRoundTopLeft || roundParams.isRoundTopRight || roundParams.isRoundBottomLeft || roundParams.isRoundBottomRight
	}

	private fun whenTopLeftRound(path: Path, radius: Float) {
		if (roundParams.isRoundTopLeft) {
			pathArc.set(viewBound.left, viewBound.top, viewBound.left + 2 * radius, viewBound.top + 2 * radius)
			path.arcTo(pathArc, 180f, 90f)
		}
	}

	private fun whenTopRightRound(path: Path, radius: Float) {
		if (roundParams.isRoundTopRight) {
			path.lineTo(viewBound.right - radius, viewBound.top)
			pathArc.set(viewBound.right - 2 * radius, viewBound.top, viewBound.right, viewBound.top + 2 * radius)
			path.arcTo(pathArc, 270f, 90f)
		} else {
			path.lineTo(viewBound.right, viewBound.top)
		}
	}

	private fun whenBottomRightRound(path: Path, radius: Float) {
		if (roundParams.isRoundBottomRight) {
			path.lineTo(viewBound.right, viewBound.bottom - radius)
			pathArc.set(viewBound.right - 2 * radius, viewBound.bottom - 2 * radius, viewBound.right, viewBound.bottom)
			path.arcTo(pathArc, 0f, 90f)
		} else {
			path.lineTo(viewBound.right, viewBound.bottom)
		}

	}

	private fun whenBottomLeftRound(path: Path, radius: Float) {
		if (roundParams.isRoundBottomLeft) {
			path.lineTo(viewBound.left + radius, viewBound.bottom)
			pathArc.set(viewBound.left, viewBound.bottom - 2 * radius, viewBound.left + 2 * radius, viewBound.bottom)
			path.arcTo(pathArc, 90f, 90f)
		} else {
			path.lineTo(viewBound.left, viewBound.bottom)
		}
	}

	private fun closePath(path: Path, radius: Float) {
		if (roundParams.isRoundTopLeft) {
			path.lineTo(viewBound.left, viewBound.top + radius)
		} else {
			path.lineTo(viewBound.left, viewBound.top)
		}
	}

	fun obtainBounds(): Path {
		if (ifRoundAsCircle(boundPath)) {
			return boundPath
		}
		val radius = roundParams.roundCornerRadius
		if (isAnyCornerHasRound()) {
			whenTopLeftRound(boundPath, radius)
			whenTopRightRound(boundPath, radius)
			whenBottomRightRound(boundPath, radius)
			whenBottomLeftRound(boundPath, radius)
			closePath(boundPath, radius)
		}
		return boundPath
	}


	fun beforeDraw(canvas: Canvas) {
		start(canvas)
		obtainBounds()
		applyBound(canvas)
	}

	fun afterDraw(canvas: Canvas) {
		completed(canvas)

	}

	fun start(canvas: Canvas) {
		canvas.save()
	}

	fun completed(canvas: Canvas) {
		canvas.restore()
		boundPath.reset()
	}

	fun applyBound(canvas: Canvas) {
		if (!boundPath.isEmpty) {
			canvas.clipPath(boundPath)
		}
	}


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