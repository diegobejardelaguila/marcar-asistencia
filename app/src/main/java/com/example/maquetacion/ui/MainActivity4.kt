package com.example.maquetacion.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.maquetacion.R
import com.example.maquetacion.model.User
import com.example.maquetacion.service.ApiService
import retrofit2.Call
import retrofit2.Response

class MainActivity4 : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create(this)
    }

    fun perfomData(){
        val callGetUserData = apiService.getUser()
        val tvFirstName = findViewById<TextView>(R.id.tvFirstName)
        val tvLastName = findViewById<TextView>(R.id.tvLastName)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvNumeroTelefono = findViewById<TextView>(R.id.tvNumeroTelefono)
        val tvDni = findViewById<TextView>(R.id.tvDni)
        val imgFace = findViewById<ImageView>(R.id.imgFace)



        callGetUserData.enqueue(object : retrofit2.Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity4,
                    "Hubo un error en el servidor",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user: User?=response.body()
                    tvFirstName.setText(user?.first_name)
                    tvLastName.setText(user?.last_name)
                    tvEmail.setText(user?.email)
                    tvNumeroTelefono.setText(user?.phone)
                    tvDni.setText((user?.dni))
                    Glide.with(this@MainActivity4).load(user?.img_profile).into(imgFace)

                } else {
                    Toast.makeText(
                        this@MainActivity4,
                        "Las credenciales son incorrectas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


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
        perfomData()

        val btnListAsistencia = findViewById<CardView>(R.id.btnVerAsistencias)
        val btnMarcacion = findViewById<CardView>(R.id.btnRealizarMarcacion)
        val btnCerrarSesion = findViewById<CardView>(R.id.btnCerrarSesion)
        val editDatos = findViewById<CardView>(R.id.editarDatos)

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

        editDatos.setOnClickListener{
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun perfomCerrarSesion () {
        val sharedPreference = getSharedPreferences("SECURITY", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("access", "")
        editor.apply()
    }
}