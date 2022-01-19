package com.example.myservices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SpecScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spec_schedule)
    }

    fun onClose(view: View) {
        finish()
    }
}