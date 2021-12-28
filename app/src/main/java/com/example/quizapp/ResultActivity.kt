package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvScore = findViewById<TextView>(R.id.tv_score)
        val btnFinish = findViewById<Button>(R.id.btn_finish)

        val username = intent.getStringExtra(Constants.USER_NAME)
        val score = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val total = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        tvName.text = username
        tvScore.text = "You got $score out of $total correct!"

        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}