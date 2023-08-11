package com.example.maquetacion.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.maquetacion.R
import com.example.maquetacion.model.login.LoginResponse
import com.example.maquetacion.service.ApiService
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnVista6 = findViewById<Button>(R.id.btn_login)

        btnVista6.setOnClickListener{
            perfomLogin()
        }
    }

    private fun createSessionPreference(jwt: String) {
        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("JWT", jwt)
        editor.apply()
    }

    private fun perfomLogin() {
        val etEmail = findViewById<EditText>(R.id.et_email).text.toString()
        val etPassword = findViewById<EditText>(R.id.et_password).text.toString()

        val call = apiService.postLogin(etEmail, etPassword)
        call.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Hubo un error en el servidor", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Log.d("Login", "Token: ${loginResponse}")
                    createSessionPreference(loginResponse?.access.toString())

                    Toast.makeText(this@MainActivity, "Token: ${loginResponse?.access}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MainActivity, MainActivity2::class.java)
                    startActivity(intent)
                } else {

                    Toast.makeText(this@MainActivity, "Las credenciales son incorrectas", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}