package com.example.stackviewframeworkapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var demoStackLayout: StackLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        demoStackLayout = findViewById(R.id.demo_sl)
        explore_btn.setOnClickListener {
            explore_btn.visibility = View.GONE
            demoStackLayout.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        if (demoStackLayout.isVisible) {
            if (demoStackLayout.currentActive == 0) {
                demoStackLayout.visibility = View.GONE
             //   demoStackLayout.clearData()
                explore_btn.visibility = View.VISIBLE
            } else {
                demoStackLayout.handleBack()
            }
        } else {
            super.onBackPressed()
        }
    }
}