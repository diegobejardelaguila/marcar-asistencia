package com.example.maquetacion.io.response

import com.example.maquetacion.model.Asistencia

data class AsistenciaResponse (
    val data: List<Asistencia>,
    val success: Boolean
)