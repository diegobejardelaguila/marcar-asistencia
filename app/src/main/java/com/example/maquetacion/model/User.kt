package com.example.maquetacion.model

data class User(
    val id: Int?,
    val email: String?,
    val img_profile: String?,
    val phone: String?,
    val dni: String?,
    val first_name: String?,
    val last_name: String?,
    val expected_entry_time: String?,
    val expected_exit_time: String?,
    val is_active: Boolean?,
    val is_staff: Boolean?
)
