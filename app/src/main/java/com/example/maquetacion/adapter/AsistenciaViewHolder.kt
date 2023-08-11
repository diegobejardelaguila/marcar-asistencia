package com.example.maquetacion.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.maquetacion.R
import com.example.maquetacion.model.Asistencia

class AsistenciaViewHolder(val view: View):ViewHolder(view) {

    val fecha = view.findViewById<TextView>(R.id.tvFecha)
    val entrada = view.findViewById<TextView>(R.id.tvEntrada)
    val salida = view.findViewById<TextView>(R.id.tvSalida)

    fun render(asistencia: Asistencia) {
        fecha.text = asistencia.fecha
        entrada.text = asistencia.entry_time
        salida.text = asistencia.exit_time

    }
}