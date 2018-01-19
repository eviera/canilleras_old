package net.eviera.canilleras

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.alexvasilkov.gestures.Settings
import kotlinx.android.synthetic.main.activity_gestures_view_demo.*

class GesturesViewDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestures_view_demo)

        gestureView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cani))

        /*
        gestureView.controller.settings.isRotationEnabled = true
        gestureView.controller.settings.isFillViewport = false;
*/

        gestureView.controller.settings
                .setMovementArea(100, 100)
                .setMaxZoom(2f)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setRotationEnabled(false)
                .setRestrictRotation(false)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(5f)
                .setFillViewport(false)
                .setFitMethod(Settings.Fit.OUTSIDE)
                ;

    }
}
