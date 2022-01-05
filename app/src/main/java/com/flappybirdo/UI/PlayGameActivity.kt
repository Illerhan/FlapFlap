package com.flappybirdo.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PlayGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playView = PlayView(this)
        setContentView(playView)
    }
}