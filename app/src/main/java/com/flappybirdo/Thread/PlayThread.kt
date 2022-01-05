package com.flappybirdo.Thread

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import androidx.core.graphics.scale
import com.flappybirdo.Model.BackgroundImage
import com.flappybirdo.Model.Bird
import com.flappybirdo.Model.ScreenSize
import com.flappybirdo.R

internal class PlayThread : Thread {

    private val TAG : String = "PlayThread"
    private var holder : SurfaceHolder
    private var resources : Resources
    var isRunning : Boolean = false
        get() = field
        set(value) {
            field = value
        }
    private val FPS : Int = (1000.0/60.0).toInt()
    private val backgroundImage = BackgroundImage()
    private var bitmapImage : Bitmap? = null
    private var startTime : Long = 0
    private var frameTime : Long = 0
    private val velocity = 3
    private val bird : Bird
    // game: 0 = stop / game: 1 = running / game: 2 = game over
    private var state : Int = 0
    private var velocityBird : Int = 0

    constructor(holder: SurfaceHolder, resources: Resources) {
        this.holder = holder
        this.resources = resources
        isRunning = true
        bird = Bird(resources)
        bitmapImage = BitmapFactory.decodeResource(resources, R.drawable.run_background)
        bitmapImage = bitmapImage?.let { ScaleResize(it) }
    }

    override fun run() {
        Log.d(TAG," Thread Started")
        while (isRunning) {
            if (holder == null) return
            startTime = System.nanoTime()
            val canvas = holder.lockCanvas()
            if(canvas != null){
                try {
                    synchronized(holder) {
                        render(canvas)
                        RenderBird(canvas)
                    }
                }
                finally {
                    holder.unlockCanvasAndPost(canvas)
                }
            }
            frameTime = (System.nanoTime() - startTime)/1000000
            if (frameTime < FPS){
                try {
                    Thread.sleep(FPS - frameTime)
                }catch (e: InterruptedException) {
                    Log.e("Interrupted","Thread sleep error")
                }
            }
        }
        Log.d(TAG, "Thread finish")
    }

    private fun RenderBird(canvas: Canvas?) {
        if (state == 1){
            if (bird.y < (ScreenSize.SCREEN_HEIGHT - bird.getBird(0).height) || velocityBird < 0){
                velocityBird = velocityBird + 2 //Descend
                bird.y = bird.y + velocityBird //Monte
            }
        }

        var current : Int = bird.currentFrame
        canvas!!.drawBitmap(bird.getBird(current),bird.x.toFloat(),bird.y.toFloat(),null)
        current++
        if (current > bird.maxFrame)
            current = 0
        bird.currentFrame = current
    }

    private fun render(canvas: Canvas?) {
        Log.d(TAG, "Render canvas")
        backgroundImage.x = backgroundImage.x - velocity
        if(backgroundImage.x < -bitmapImage!!.width){
            backgroundImage.x = 0
        }

        bitmapImage?.let { canvas!!.drawBitmap(it,(backgroundImage.x).toFloat(),(backgroundImage.y).toFloat(),null) }

        //loop de l'image
        if(backgroundImage.x < -(bitmapImage!!.width + ScreenSize.SCREEN_WIDTH)) {
            bitmapImage?.let { canvas!!.drawBitmap(it, (backgroundImage.x + bitmapImage!!.width).toFloat(), (backgroundImage.y).toFloat(),null) }
        }

    }

    private fun ScaleResize(bitmap: Bitmap): Bitmap {
        var ratio : Float = (bitmap.width / bitmap.height).toFloat()
        val scaleWidth : Int = (ratio + ScreenSize.SCREEN_HEIGHT).toInt()
        return Bitmap.createScaledBitmap(bitmap,scaleWidth,ScreenSize.SCREEN_HEIGHT,false)
    }

    fun Jump() {
        state = 1
        //Bloque l'oiseau s'il arrive en haut de l'écran
        if(bird.y > 0){
            velocityBird = - 30
        }
    }
}