package com.example.maquetacion.ui
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.maquetacion.R
import com.example.maquetacion.service.ApiService
import com.example.maquetacion.model.User
import com.example.maquetacion.model.login.LoginResponse
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
                    val user:User?=response.body()
                    tvFirstName.setText(user?.first_name)
                    tvLastName.setText(user?.last_name)
                    tvEmail.setText(user?.email)
                    println(user)
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

        val btnListAsistencia = findViewById<Button>(R.id.btnVerAsistencias)
        val btnMarcacion = findViewById<Button>(R.id.btnRealizarMarcacion)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

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