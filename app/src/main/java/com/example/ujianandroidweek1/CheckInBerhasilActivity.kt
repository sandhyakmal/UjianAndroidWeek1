package com.example.ujianandroidweek1

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_check_in.*
import kotlinx.android.synthetic.main.activity_check_in_berhasil.*
import kotlinx.android.synthetic.main.activity_check_in_gagal.*
import kotlinx.android.synthetic.main.activity_main.*

class CheckInBerhasilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in_berhasil)

        btnDone.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        })
    }



}