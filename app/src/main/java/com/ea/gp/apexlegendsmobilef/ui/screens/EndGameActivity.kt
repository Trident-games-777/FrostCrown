package com.ea.gp.apexlegendsmobilef.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ea.gp.apexlegendsmobilef.R

class EndGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        val result = intent.getBooleanExtra("extra_result", false)
        findViewById<TextView>(R.id.tvResult).text = if (result) "You won" else "You lose"
        findViewById<Button>(R.id.btPlay).setOnClickListener {
            val intent = Intent(this@EndGameActivity, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}