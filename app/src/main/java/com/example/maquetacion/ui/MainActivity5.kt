package com.example.maquetacion.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.maquetacion.R
import com.example.maquetacion.service.ApiService
import retrofit2.Call
import retrofit2.Response
import android.widget.Toast
import com.example.maquetacion.io.response.AsistenciaCreateResponse


class MainActivity5 : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val imageView = findViewById<ImageView>(R.id.btn_regresar_vista)

        imageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity5, MainActivity4::class.java)
                startActivity(intent)
            }
        }
        )

        val btnMarcar = findViewById<Button>(R.id.btnMarcar)
        btnMarcar.setOnClickListener{
            registerAsistencia()

        }
    }
    fun registerAsistencia(){
        val callRegistreData = apiService.postAsistencia()

        callRegistreData.enqueue(object : retrofit2.Callback<AsistenciaCreateResponse> {
            override fun onFailure(call: Call<AsistenciaCreateResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity5,
                    "Hubo un error en el servidor",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<AsistenciaCreateResponse>, response: Response<AsistenciaCreateResponse>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@MainActivity5, MainActivity6::class.java)
                    startActivity(intent)
                    println(response.body())
                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity5,
                        "Las credenciales son incorrectas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


}