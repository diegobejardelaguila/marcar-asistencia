package com.example.maquetacion.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maquetacion.R
import com.example.maquetacion.adapter.AsistenciaAdapter
import com.example.maquetacion.io.response.AsistenciaResponse
import com.example.maquetacion.model.Asistencia
import com.example.maquetacion.provider.AsistenciaProvider
import com.example.maquetacion.service.ApiService
import retrofit2.Call
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create(this)
    }

    private val asistenciaList: MutableList<Asistencia> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        perfomAsistencia()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerAsistencias)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AsistenciaAdapter(asistenciaList)
    }

    private fun perfomAsistencia() {
        val call = apiService.getAsistencias()
        call.enqueue(object : retrofit2.Callback<AsistenciaResponse> {
            override fun onResponse(
                call: Call<AsistenciaResponse>,
                response: Response<AsistenciaResponse>
            ) {
                if (response.isSuccessful) {
                    val asistenciaResponse = response.body()
                    asistenciaResponse?.data?.let {
                        // Agregar la lista de asistencias al asistenciaList de la actividad
                        asistenciaList.addAll(it)

                        // Notificar al adaptador que los datos cambiaron
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerAsistencias)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                } else {
                    println("Error en la respuesta del servidor")
                }
            }

            override fun onFailure(call: Call<AsistenciaResponse>, t: Throwable) {
                println(
                    "Error: ${t.message}"
                )
            }

        })
    }

}