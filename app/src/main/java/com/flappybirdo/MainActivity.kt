package com.flappybirdo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.flappybirdo.UI.PlayGameActivity
import java.util.logging.Level.INFO

class MainActivity : AppCompatActivity() {
    private val Tag = "MainActivity"

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPlay = findViewById<ImageButton>(R.id.btnPlay);

        btnPlay.setOnClickListener {
            val iPlayGame = Intent(this@MainActivity,PlayGameActivity::class.java)
            startActivity(iPlayGame)
            finish()
            Log.d(Tag, "Button Play clicked")
        }
    }
}
