package com.example.maquetacion.service

import android.content.Context
import com.example.maquetacion.io.response.AsistenciaResponse
import com.example.maquetacion.model.login.LoginResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        fun create(context: Context): ApiService {
            val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
                val sharedPreferences = context.getSharedPreferences("SECURITY", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("access", "")
                println("estamos en el interceptor")
                println(token)

                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "JWT $token")
                    .build()

                chain.proceed(request)
            }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiService::class.java)
        }

    }

}