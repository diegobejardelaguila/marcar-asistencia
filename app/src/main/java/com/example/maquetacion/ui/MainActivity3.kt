package com.example.maquetacion.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.maquetacion.R
import com.example.maquetacion.model.User
import com.example.maquetacion.model.login.LoginResponse
import com.example.maquetacion.service.ApiService
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class MainActivity3 : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create(this)
    }

    var validUpdate = true
    var userData: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        perfomGetUser()

        val etTelefono = findViewById<EditText>(R.id.etTelefono)

        etTelefono.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s?.length ?: 0 < 9 || s?.length ?: 0 > 9) {
                    etTelefono.error = "El teléfono debe tener 9 dígitos"
                    validUpdate = false
                } else {
                    etTelefono.error = null
                    validUpdate = true
                }
            }
        })

        val regreMenu = findViewById<ImageView>(R.id.ivRegresar)
        val guardarCambios = findViewById<Button>(R.id.btn_login)

        regreMenu.setOnClickListener{
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
            finish()
        }

        guardarCambios.setOnClickListener{
            perfomUpdate()
        }
    }


    fun perfomGetUser() {
        val callGetUserData = apiService.getUser()
        val etFirstName = findViewById<EditText>(R.id.etNombres)
        val etLastName = findViewById<EditText>(R.id.etApellidos)
        val etPhone = findViewById<EditText>(R.id.etTelefono)
        val etDni = findViewById<EditText>(R.id.etDNI)
        val etEmail = findViewById<EditText>(R.id.etCorreo)


        callGetUserData.enqueue(object : retrofit2.Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity3,
                    "Hubo un error en el servidor",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    var userResponse = response.body()
                    userData = User(
                        id = userResponse?.id,
                        email = userResponse?.email,
                        img_profile = userResponse?.img_profile,
                        phone = userResponse?.phone,
                        dni = userResponse?.dni,
                        first_name = userResponse?.first_name,
                        last_name = userResponse?.last_name,
                        expected_entry_time = userResponse?.expected_entry_time,
                        expected_exit_time = userResponse?.expected_exit_time,
                        is_active   = userResponse?.is_active,
                        is_staff = userResponse?.is_staff,
                    )

                    etFirstName.setText(userResponse?.first_name)
                    etLastName.setText(userResponse?.last_name)
                    etPhone.setText(userResponse?.phone)
                    etDni.setText(userResponse?.dni)
                    etEmail.setText(userResponse?.email)

                    println(userResponse)
                } else {
                    Toast.makeText(
                        this@MainActivity3,
                        "Las credenciales son incorrectas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    fun perfomUpdate() {
        if (validUpdate) {
            val etFirstName = findViewById<EditText>(R.id.etNombres)
            val etLastName = findViewById<EditText>(R.id.etApellidos)
            val etPhone = findViewById<EditText>(R.id.etTelefono)

            val user = User(
                first_name = etFirstName.text.toString(),
                last_name = etLastName.text.toString(),
                phone = etPhone.text.toString(),
                id = userData?.id,
                email = userData?.email,
                img_profile = userData?.img_profile,
                dni = userData?.dni,
                expected_entry_time = userData?.expected_entry_time,
                expected_exit_time = userData?.expected_exit_time,
                is_active = userData?.is_active,
                is_staff = userData?.is_staff
            )

            println(user)

            val callUpdate = apiService.putUser(
                email = user.email,
                first_name = user.first_name,
                last_name = user.last_name,
                phone = user.phone,
                dni = user.dni,
                expected_entry_time = user.expected_entry_time,
                expected_exit_time = user.expected_exit_time,
                is_active = user.is_active,
                is_staff = user.is_staff
            )

            callUpdate.enqueue(object : retrofit2.Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity3,
                        "Hubo un error en el servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        Toast.makeText(
                            this@MainActivity3,
                            "Se actualizó correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null) {
                            try {
                                val errorJson = JSONObject(errorBody)
                                val errorMessage = errorJson.getString("message")
                                Toast.makeText(
                                    this@MainActivity3,
                                    "Error: $errorMessage",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (e: JSONException) {
                                Toast.makeText(
                                    this@MainActivity3,
                                    "Hubo un error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this@MainActivity3,
                                "Hubo un error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        } else {
            Toast.makeText(
                this@MainActivity3,
                "No se pudo actualizar",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}