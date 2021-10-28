package com.example.weather_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.Button
import android.widget.EditText

class MainActivity2 : AppCompatActivity() {
   lateinit var btnsubmit:Button
   lateinit var edzibcode:EditText
    var code=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btnsubmit=findViewById(R.id.btnsub)
        edzibcode = findViewById(R.id.edzibcode)
        btnsubmit.setOnClickListener {
           code = edzibcode.text.toString()
            edzibcode.text.clear()
            edzibcode.clearFocus()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}