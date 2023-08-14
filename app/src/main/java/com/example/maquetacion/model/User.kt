package com.example.maquetacion.model


/*
* {
    "id": 1,
    "email": "diego@gmail.com",
    "img_profile": "http://107.21.85.152/media/user/profile/365645293_922737879213177_8528785852632069532_n_1.jpg",
    "first_name": "Diego",
    "phone": "985214215",
    "dni": "75345612",
    "last_name": "Bejar",
    "expected_entry_time": "08:00:00",
    "expected_exit_time": "18:00:00",
    "is_active": true,
    "is_staff": true
}*
* */
data class User(
    val id: Int?,
    val email: String,
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
