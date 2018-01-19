package net.eviera.canilleras

import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var scalediff: Float = 0.toFloat()
    private val NONE = 0
    private val DRAG = 1
    private val ZOOM = 2
    private var mode = NONE
    private var oldDist = 1f
    private var d = 0f
    private var newRot = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutParams = RelativeLayout.LayoutParams(250, 250)
        layoutParams.leftMargin = 50
        layoutParams.topMargin = 50
        layoutParams.bottomMargin = -250
        layoutParams.rightMargin = -250
        im_move_zoom_rotate.layoutParams = layoutParams

        var parms: RelativeLayout.LayoutParams
        var startwidth: Int = 0
        var startheight: Int = 0
        var dx = 0f
        var dy = 0f
        var x = 0f
        var y = 0f
        var angle = 0f

        im_move_zoom_rotate.setOnTouchListener { v, event ->

            val view = v as ImageView

            (view.drawable as BitmapDrawable).setAntiAlias(true)
            parms = view.layoutParams as RelativeLayout.LayoutParams

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {

                    startwidth = parms.width
                    startheight = parms.height
                    dx = event.rawX - parms.leftMargin
                    dy = event.rawY - parms.topMargin
                    mode = DRAG
                }

                MotionEvent.ACTION_POINTER_DOWN -> {
                    oldDist = spacing(event)
                    if (oldDist > 10f) {
                        mode = ZOOM
                    }

                    d = rotation(event)
                }
                MotionEvent.ACTION_UP -> {
                }

                MotionEvent.ACTION_POINTER_UP -> mode = NONE
                MotionEvent.ACTION_MOVE -> if (mode == DRAG) {

                    x = event.rawX
                    y = event.rawY

                    parms.leftMargin = (x - dx).toInt()
                    parms.topMargin = (y - dy).toInt()

                    parms.rightMargin = 0
                    parms.bottomMargin = 0
                    parms.rightMargin = parms.leftMargin + 5 * parms.width
                    parms.bottomMargin = parms.topMargin + 10 * parms.height

                    view.layoutParams = parms

                } else if (mode == ZOOM) {

                    if (event.pointerCount == 2) {

                        newRot = rotation(event)
                        val r = newRot - d
                        angle = r

                        x = event.rawX
                        y = event.rawY

                        val newDist = spacing(event)
                        if (newDist > 10f) {
                            val scale = newDist / oldDist * view.scaleX
                            if (scale > 0.6) {
                                scalediff = scale
                                view.scaleX = scale
                                view.scaleY = scale

                            }
                        }

                        view.animate().rotationBy(angle).setDuration(0).setInterpolator(LinearInterpolator()).start()

                        x = event.rawX
                        y = event.rawY

                        parms.leftMargin = (x - dx + scalediff).toInt()
                        parms.topMargin = (y - dy + scalediff).toInt()

                        parms.rightMargin = 0
                        parms.bottomMargin = 0
                        parms.rightMargin = parms.leftMargin + 5 * parms.width
                        parms.bottomMargin = parms.topMargin + 10 * parms.height

                        view.layoutParams = parms


                    }
                }
            }

            return@setOnTouchListener true
        }


    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun rotation(event: MotionEvent): Float {
        val delta_x = (event.getX(0) - event.getX(1)).toDouble()
        val delta_y = (event.getY(0) - event.getY(1)).toDouble()
        val radians = Math.atan2(delta_y, delta_x)
        return Math.toDegrees(radians).toFloat()
    }


}
