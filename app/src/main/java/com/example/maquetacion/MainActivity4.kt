package com.example.maquetacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        val btnVistaMain = findViewById<Button>(R.id.btnCerrarSesion)
        val btnVista5 = findViewById<Button>(R.id.btnRealizarMarcacion)
        val btnVista2 = findViewById<Button>(R.id.btnVerAsistencias)

        btnVistaMain.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnVista5.setOnClickListener{
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
        }

        btnVista2.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}