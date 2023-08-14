package com.example.maquetacion.service

import android.content.Context
import com.example.maquetacion.io.response.AsistenciaCreateResponse
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
import com.example.maquetacion.model.Asistencia

interface ApiService {

    //auth
    @POST("auth/jwt/create/")
    @FormUrlEncoded
    fun postLogin(@Field("email") email: String, @Field("password") password: String): Call<LoginResponse>

    // Users
    @GET("auth/users/me/")
    fun getUser(): Call<com.example.maquetacion.model.User>

    @PUT("auth/users/me/")
    @FormUrlEncoded
    fun putUser(
        @Field("email") email: String?,
        @Field("phone") phone: String?,
        @Field("dni") dni: String?,
        @Field("first_name") first_name: String?,
        @Field("last_name") last_name: String?,
        @Field("expected_entry_time") expected_entry_time: String?,
        @Field("expected_exit_time") expected_exit_time: String?,
        @Field("is_active") is_active: Boolean?,
        @Field("is_staff") is_staff: Boolean?
    ): Call<com.example.maquetacion.model.User>


    // Asistencia
    @GET("asistencia/")
    fun getAsistencias(): Call<AsistenciaResponse>

    @GET("asistencia/{id}/")
    fun getAsistencia(@Query("id") id: Int): Call<com.example.maquetacion.model.Asistencia>

    @POST("asistencia/")
    fun postAsistencia(
    ): Call<AsistenciaCreateResponse>

    @PUT("asistencia/{id}/")
    @FormUrlEncoded
    fun putAsistencia(
        @Field("fecha") fecha: String?,
        @Field("hora") hora: String?,
        @Field("tipo") tipo: String?,
        @Field("user") user: Int?
    ): Call<com.example.maquetacion.model.Asistencia>

    companion object Factory {
        const val BASE_URL = "http://107.21.85.152/api/"
        fun create(context: Context): ApiService {
            // Interceptor
            val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
                val sharedPreferences = context.getSharedPreferences("SECURITY", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("access", "")

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