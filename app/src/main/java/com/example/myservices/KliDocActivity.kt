package com.example.myservices

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class KliDocActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kli_doc)
    }



    fun onClose(view: View) {
        finish()
    }


}