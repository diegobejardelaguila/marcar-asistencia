package com.example.maquetacion.model
import java.time.LocalTime

data class Asistencia(
    val id: Int,
    val fecha: String?,
    val entry_time: String?,
    val exit_time: String?,
    val user: Int?
)