package com.example.ujianandroidweek1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnCheckIn.setOnClickListener(View.OnClickListener {
            onCheckIn(it)
        })

        btnIzin.setOnClickListener(View.OnClickListener {
            onIzin(it)
        })
    }

    fun onCheckIn(v: View){

        val intent = Intent(this,CheckInActivity::class.java)
        startActivity(intent)

    }

    fun onIzin(v: View){

        val intent = Intent(this,IzinActivity::class.java)
        startActivity(intent)

    }
}