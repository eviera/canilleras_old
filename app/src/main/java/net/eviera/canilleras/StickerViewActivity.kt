package net.eviera.canilleras

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sticker_view.*
import net.eviera.canilleras.stickerview.StickerImageView
import net.eviera.canilleras.stickerview.StickerTextView


class StickerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticker_view)

        val iv_sticker = StickerImageView(this)
        iv_sticker.setImageDrawable(resources.getDrawable(R.drawable.cani))
        canvasView.addView(iv_sticker)

        val tv_sticker = StickerTextView(this)
        tv_sticker.text = "nkDroid"
        canvasView.addView(tv_sticker)
    }
}
