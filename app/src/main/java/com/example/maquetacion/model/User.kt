package com.example.maquetacion.model

data class User(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val is_active: Boolean,
    val is_staff: Boolean
)
