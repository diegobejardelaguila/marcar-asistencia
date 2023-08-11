package com.example.maquetacion.service

import com.example.maquetacion.io.response.AsistenciaResponse
import com.example.maquetacion.model.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    //auth
    @POST("auth/jwt/create/")
    @FormUrlEncoded
    fun postLogin(@Field("email") email: String, @Field("password") password: String): Call<LoginResponse>

    // Users
    @GET("auth/users/")
    fun getUser(): Call<List<com.example.maquetacion.model.User>>

    // Asistencia
    @GET("asistencia/")
    fun getAsistencias(): Call<AsistenciaResponse>

    @GET("asistencia/{id}/")
    fun getAsistencia(@Query("id") id: Int): Call<com.example.maquetacion.model.Asistencia>

    @POST("asistencia/")
    @FormUrlEncoded
    fun postAsistencia(
        @Field("fecha") fecha: String?,
        @Field("hora") hora: String?,
        @Field("tipo") tipo: String?,
        @Field("user") user: Int?
    ): Call<com.example.maquetacion.model.Asistencia>

    @PUT("asistencia/{id}/")
    @FormUrlEncoded
    fun putAsistencia(
        @Field("fecha") fecha: String?,
        @Field("hora") hora: String?,
        @Field("tipo") tipo: String?,
        @Field("user") user: Int?
    ): Call<com.example.maquetacion.model.Asistencia>

    companion object Factory {
        const val BASE_URL = "http://10.0.2.2:8000/api/"
        fun create(): ApiService {
            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }

    }

}