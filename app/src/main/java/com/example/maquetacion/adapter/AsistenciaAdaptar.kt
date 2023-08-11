package com.example.maquetacion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maquetacion.R
import com.example.maquetacion.model.Asistencia

class AsistenciaAdapter(private val asistenciaList:List<Asistencia>) : RecyclerView.Adapter<AsistenciaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsistenciaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AsistenciaViewHolder(layoutInflater.inflate(R.layout.item_asistencia, parent, false))
    }

    override fun onBindViewHolder(holder: AsistenciaViewHolder, position: Int) {
        val item = asistenciaList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = asistenciaList.size


}