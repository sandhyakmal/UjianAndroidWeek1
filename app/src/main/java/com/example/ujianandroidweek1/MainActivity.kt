package com.example.ujianandroidweek1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val defaultUsername = "Sandhy"
    val defaulPassword = "123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener(View.OnClickListener {
            checkLogin(it)
        })
    }

    fun checkLogin(v: View){

        if (txtUsername.text.contentEquals(defaultUsername) && txtPassword.text.contentEquals(defaulPassword)){

            val intent = Intent(this,MenuActivity::class.java)
            intent.putExtra("username",txtUsername.text.toString())
            intent.putExtra("password",txtPassword.text.toString())
            startActivity(intent)

            Toast.makeText(applicationContext, "Login Berhasil", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Username atau Password salah!!!", Toast.LENGTH_SHORT).show()
        }

    }
}