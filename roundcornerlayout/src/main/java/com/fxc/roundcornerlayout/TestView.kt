package com.fxc.roundcornerlayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * @author fxc
 * @date 2018/4/12
 */
class TestView : View {
	constructor(context: Context?) : super(context)
	constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
	constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		viewBound.set(getViewBound())
		val path =obtainBounds()
		val paint = Paint()
//		paint.style = Paint.Style.STROKE
		paint.color = Color.BLACK
//		path.addArc(arc, 0f, 180f)
		canvas.drawPath(path, paint)
	}

	private val pathArc = RectF()
	private val viewBound: RectF = getViewBound()

	private fun getViewBound(): RectF {
		return RectF(paddingLeft.toFloat(), paddingTop.toFloat(), (width - paddingRight).toFloat(), (height - paddingBottom).toFloat())
	}

	private fun obtainBounds(): Path {
		val path = Path()
		val p = Path()
		p.addRect(viewBound, Path.Direction.CW)
//		path.fillType = Path.FillType.INVERSE_WINDING
		val radius = 100
		pathArc.set(viewBound.left, viewBound.top, viewBound.left + 2 * radius, viewBound.top + 2 * radius)
		path.arcTo(pathArc, 180f, 90f, true)
//		path.moveTo(viewBound.left + radius, viewBound.top)

		path.lineTo(viewBound.right - radius, viewBound.top)

		pathArc.set(viewBound.right - 2 * radius, viewBound.top, viewBound.right, viewBound.top + 2 * radius)
		path.arcTo(pathArc, 270f, 90f, true)
//		path.moveTo(viewBound.right, viewBound.top + radius)


		path.lineTo(viewBound.right, viewBound.bottom - radius)
		pathArc.set(viewBound.right - 2 * radius, viewBound.bottom - 2 * radius, viewBound.right, viewBound.bottom)
		path.arcTo(pathArc, 0f, 90f, true)
//		path.moveTo(viewBound.right - radius, viewBound.bottom)


		path.lineTo(viewBound.left + radius, viewBound.bottom)
		pathArc.set(viewBound.left, viewBound.bottom - 2 * radius, viewBound.left + 2 * radius, viewBound.bottom)
		path.arcTo(pathArc, 90f, 90f, true)
//		path.moveTo(viewBound.left, viewBound.bottom - radius)

		path.lineTo(viewBound.left, viewBound.top + radius)
		path.close()
		return path
	}
}