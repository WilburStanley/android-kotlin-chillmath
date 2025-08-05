package com.techvalentine.chillmathgame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MathOperations : AppCompatActivity() {
    private lateinit var goBackToMainBtn: AppCompatImageButton

    private lateinit var multiplicationTableView: GridView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_math_operations)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mathOperations)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initialization()

        multiplicationTableView.adapter = GridAdapter()

        goBackToMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            AppUtility.goToNewPageWithAnimation(this, intent)
        }
    }
    inner class GridAdapter : BaseAdapter() {
        // Generate formatted multiplication table strings for numbers 1 to 10
        private val items: List<String> = List(10) { row ->
            // Create a string for each number from 1 to 10
            val number = row + 1
            val multiplicationResults = StringBuilder()
            // Append each multiplication result to the StringBuilder
            for (col in 1..10) {
                multiplicationResults.append("$number x $col = ${number * col}")
                // Add a line break after each multiplication (except the last one)
                if (col < 10) {
                    multiplicationResults.append("\n")
                }
            }
            multiplicationResults.toString() // Convert StringBuilder to String
        }

        override fun getCount(): Int = items.size

        override fun getItem(position: Int): Any = items[position]

        override fun getItemId(position: Int): Long = position.toLong()

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.grid_item, parent, false)
            val textView = view.findViewById<TextView>(R.id.gridItemLabel)
            textView.text = items[position]
            return view
        }
    }
    private fun initialization() {
        goBackToMainBtn = findViewById(R.id.goBackToMainBtn)
        multiplicationTableView = findViewById(R.id.multiplicationTableView)
    }
}