package com.flappybirdo.UI

import android.content.Context
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.flappybirdo.Thread.PlayThread

class PlayView(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {

    private val Tag = "PlayView"
    private var playThread : PlayThread? = null


    init {
        val holder = holder
        holder.addCallback(this)
        isFocusable = true
        playThread = PlayThread(holder,resources)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        if (playThread!!.isRunning){
            playThread!!.isRunning = false
            var isCheck : Boolean = true
            while (isCheck){
                try {
                    playThread!!.join()
                }catch (e: InterruptedException){

                }
            }
        }
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        if(!playThread!!.isRunning) {
            playThread = PlayThread(p0!!, resources)
        }else{
            playThread!!.start()
        }
    }


}