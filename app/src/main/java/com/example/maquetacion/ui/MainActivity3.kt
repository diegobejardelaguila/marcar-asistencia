package com.example.maquetacion.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.maquetacion.R

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val regreMenu = findViewById<ImageView>(R.id.ivRegresar)
        val guardarCambios = findViewById<Button>(R.id.btn_login)

        regreMenu.setOnClickListener{
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
            finish()
        }

        guardarCambios.setOnClickListener{
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
            finish()
        }

    }
}