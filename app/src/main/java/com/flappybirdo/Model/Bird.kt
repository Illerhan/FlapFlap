package com.flappybirdo.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.flappybirdo.R

class Bird (res : Resources) {
    var x : Int = 0
        get() = field
        set(value) {
            field = value
        }
    var y : Int = 0
        get() = field
        set(value) {
            field = value
        }
    val maxFrame : Int = 7
    var currentFrame : Int = 0
        get() = field
        set(value) {
            field = value
        }
    var birdList : ArrayList<Bitmap>

    init {
        birdList = arrayListOf()
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_0))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_1))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_2))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_3))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_4))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_5))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_6))
        birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_7))
        
        x = ScreenSize.SCREEN_WIDTH/2 - birdList[0].width/2
        y = ScreenSize.SCREEN_HEIGHT/2 - birdList[0].width/2
    }
    fun getBird(current : Int) : Bitmap{
        return birdList.get(current)
    }
}