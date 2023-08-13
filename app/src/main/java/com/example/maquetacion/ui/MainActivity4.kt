package com.example.maquetacion.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView
import com.example.maquetacion.R

class MainActivity4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreference = getSharedPreferences("SECURITY", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreference.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual para evitar volver atrás después de iniciar sesión
            return
        }

        setContentView(R.layout.activity_main4)

        val btnListAsistencia = findViewById<CardView>(R.id.btnVerAsistencias)
        val btnMarcacion = findViewById<CardView>(R.id.btnRealizarMarcacion)
        val btnCerrarSesion = findViewById<CardView>(R.id.btnCerrarSesion)

        btnListAsistencia.setOnClickListener{
            val intent = Intent(this@MainActivity4, MainActivity2::class.java)
            startActivity(intent)
        }

        btnMarcacion.setOnClickListener{
            val intent = Intent(this@MainActivity4, MainActivity5::class.java)
            startActivity(intent)
        }

        btnCerrarSesion.setOnClickListener{
            perfomCerrarSesion()
            val intent = Intent(this@MainActivity4, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    fun perfomCerrarSesion () {
        val sharedPreference = getSharedPreferences("SECURITY", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("access", "")
        editor.apply()
    }
}