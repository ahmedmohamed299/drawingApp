package com.example.drawingapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.ArrayList


class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    enum class Drawer {
        PEN, LINE, RECTANGLE, CIRCLE
    }
    private var mDrawPaint: Paint? = null
    private var mCanvasPaint: Paint? = null
    private var mDrawPath: CustomPath? = null
    private var startX = 0f
    private var startY = 0f
    private var color = Color.BLACK
    private val mPaths = ArrayList<CustomPath>()
    private val mUndoPaths = ArrayList<CustomPath>()
    private var drawer: Drawer
    private var mCanvasBitmap: Bitmap? = null
    private var canvas: Canvas? = null


    init {
        drawer=Drawer.PEN
        mDrawPaint = Paint()
        mDrawPaint!!.color = color
        mDrawPaint!!.strokeWidth = 10f
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPath = CustomPath(color)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)

        for (p in mPaths) {

            mDrawPaint!!.color = p.color
            canvas!!.drawPath(p, mDrawPaint!!)
        }

        if (!mDrawPath!!.isEmpty) {

            mDrawPaint!!.color = mDrawPath!!.color
            canvas!!.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, wprev: Int, hprev: Int) {
        super.onSizeChanged(w, h, wprev, hprev)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)


    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {


        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.color = color

                mDrawPath!!.reset()
                touchDown(event)
                invalidate()


            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(event)
                invalidate()

            }

            MotionEvent.ACTION_UP -> {

                mPaths.add(mDrawPath!!) //Add when to stroke is drawn to canvas and added in the path arraylist

                mDrawPath = CustomPath(color)
                invalidate()


            }
            else -> return false
        }
        invalidate()
        return true
    }

    private fun touchMove(event: MotionEvent) {
        val x = event.x
        val y = event.y


        when (drawer) {
            Drawer.PEN -> {
                mDrawPath!!.lineTo(x, y)
            }
            Drawer.LINE -> {
                mDrawPath!!.reset()
                mDrawPath!!.moveTo(startX, startY)
                mDrawPath!!.lineTo(x, y)
            }


            Drawer.RECTANGLE -> {
                mDrawPath!!.reset()
                val left = Math.min(startX, x)
                val right = Math.max(startX, x)
                val top = Math.min(startY, y)
                val bottom = Math.max(startY, y)
                mDrawPath!!.addRect(left, top, right, bottom, Path.Direction.CCW)
            }


            Drawer.CIRCLE -> {
                mDrawPath!!.reset()
                val rect = RectF(startX, startY, x, y)
                mDrawPath!!.addOval(rect, Path.Direction.CCW)

            }

        }

    }

    private fun touchDown(event: MotionEvent) {
        startX = event.x
        startY = event.y
        mDrawPath!!.moveTo(startX, startY)

    }

    fun setColor(newColor: String) {
        this.color =Color.parseColor(newColor)
        mDrawPaint!!.color = color
    }

    fun setDrawer(drawer: Drawer?) {
        this.drawer = drawer!!
    }


    fun onClickUndo() {
        if (mPaths.size > 0) {

            mUndoPaths.add(mPaths.removeAt(mPaths.size - 1))
            invalidate() // Invalidate the whole view. If the view is visible
        }
    }
    internal inner class CustomPath(var color: Int) : Path()
}