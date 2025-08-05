package com.techvalentine.chillmathgame

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var mathOperationsBtn: AppCompatButton
    private lateinit var endlessBtn: AppCompatButton
    private lateinit var integerRulesBtn: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initialization()

        endlessBtn.setOnClickListener {
            val intent = Intent(this, Endless::class.java)
            AppUtility.goToNewPageWithAnimation(this, intent)
        }

        mathOperationsBtn.setOnClickListener {
            val intent = Intent(this, MathOperations::class.java)
            AppUtility.goToNewPageWithAnimation(this, intent)
        }

        integerRulesBtn.setOnClickListener {
            val intent = Intent(this, IntegerRules::class.java)
            AppUtility.goToNewPageWithAnimation(this, intent)
        }
    }

    private fun initialization() {
        mathOperationsBtn = findViewById(R.id.mathOperationsBtn)
        endlessBtn = findViewById(R.id.endlessBtn)
        integerRulesBtn = findViewById(R.id.integerRulesBtn)

        // Touch Effects
        AppUtility.setButtonTouchEffect(mathOperationsBtn)
        AppUtility.setButtonTouchEffect(endlessBtn)
        AppUtility.setButtonTouchEffect(integerRulesBtn)
    }
}