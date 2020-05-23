package io.github.faheemezani.drawdimensions

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

const val SPACE_SIZE = 50 // pixels

class DimensionGLView (private val mContext: Context, mAttrs: AttributeSet) : View(mContext, mAttrs) {

//    private var renderer: DimensionGLRenderer
    private var myDimension: Dimension? = null
    private var mImage: Drawable? = null

    init {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusable = View.FOCUSABLE
        }

        // initializing image resource
        mImage = ContextCompat.getDrawable(mContext, R.drawable.vangogh)

        // default selected dimension to 2x2
        myDimension = Dimension.TWO_BY_TWO

    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.drawColor(Color.WHITE)

        val p = Paint()
        p.style = Paint.Style.FILL
        p.color = Color.GREEN

        when (myDimension) {

            Dimension.TWO_BY_TWO -> {
                val sqWidthOrHeight = ((width.toFloat()-(SPACE_SIZE*3))/2)
                for (i in 1..2) {
                    for (j in 1..2) {
                        drawDrawable(canvas!!, i, j, sqWidthOrHeight)
                    }
                }
            }

            Dimension.THREE_BY_THREE -> {
                val sqWidthOrHeight = ((width.toFloat()-(SPACE_SIZE*4))/3)
                for (i in 1..3) {
                    for (j in 1..3) {
                        drawDrawable(canvas!!, i, j, sqWidthOrHeight)
                    }
                }
            }

        }

    }

    private fun drawImage(canvas: Canvas, i: Int, j: Int, sqWidthOrHeight: Float, p: Paint) {
        canvas.drawRect(
            (SPACE_SIZE.toFloat()*i) + (sqWidthOrHeight*(i-1)), // left
            (SPACE_SIZE.toFloat()*j) + (sqWidthOrHeight*(j-1)), // top
            (SPACE_SIZE.toFloat()*i) + (sqWidthOrHeight*i), // right
            (SPACE_SIZE.toFloat()*j) + (sqWidthOrHeight*j), // bottom
            p
        )
    }

    private fun drawDrawable(canvas: Canvas, i: Int, j: Int, sqWidthOrHeight: Float) {
        val left = ((SPACE_SIZE.toFloat()*i) + (sqWidthOrHeight*(i-1))).toInt()
        val top = ((SPACE_SIZE.toFloat()*j) + (sqWidthOrHeight*(j-1))).toInt()
        val right = ((SPACE_SIZE.toFloat()*i) + (sqWidthOrHeight*i)).toInt()
        val bottom = ((SPACE_SIZE.toFloat()*j) + (sqWidthOrHeight*j)).toInt()
        mImage?.setBounds(left, top, right, bottom)
        mImage?.draw(canvas)
    }

    fun draw(dimen: Dimension) {
        myDimension = dimen
        invalidate()
    }

}

enum class Dimension {
    TWO_BY_TWO, THREE_BY_THREE
}