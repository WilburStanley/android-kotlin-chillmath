package com.techvalentine.chillmathgame

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IntegerRules : AppCompatActivity() {
    private lateinit var goBackToMainBtn: AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_integer_rules)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.integerRules)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialization()

        goBackToMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            AppUtility.goToNewPageWithAnimation(this, intent)
        }
    }
    private fun initialization() {
        goBackToMainBtn = findViewById(R.id.goBackToMainBtn)
    }
}