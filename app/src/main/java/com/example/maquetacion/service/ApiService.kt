package com.example.maquetacion.service

import com.example.maquetacion.model.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //auth
    @POST("auth/jwt/create/")
    @FormUrlEncoded
    fun postLogin(@Field("email") email: String, @Field("password") password: String): Call<LoginResponse>





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