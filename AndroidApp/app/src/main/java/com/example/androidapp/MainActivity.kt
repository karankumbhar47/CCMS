package com.example.androidapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.openapi.studentPortal.api.DefaultApi
import com.openapi.studentPortal.invoker.ApiException
import com.openapi.studentPortal.model.LoginRequest

class MainActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var defaultApi: DefaultApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // requestQueue = Volley.newRequestQueue(this)
        // defaultApi = DefaultApi() // Instantiate your generated DefaultApi

        editTextUsername = findViewById(R.id.userNameEditText)
        editTextPassword = findViewById(R.id.passwordEditText)
        buttonLogin = findViewById(R.id.LoginBtn)
        defaultApi = DefaultApi()

        buttonLogin.setOnClickListener {
            //val username = editTextUsername.text.toString()
            //val password = editTextPassword.text.toString()

            ////adding while loop for handling error
            //if (username == "user" && password == "1234") {
            //    Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_SHORT).show()
            //    val intent = Intent(this, DashBoardActivity::class.java)
            //    startActivity(intent)
            //} else {
            //    Toast.makeText(this@MainActivity, "Login Failed", Toast.LENGTH_SHORT).show()
            //}

            login()
        }
    }
    private fun login() {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        val loginRequest = LoginRequest().apply {
            setUsername(username)
            setPassword(password)
            //role
        }

        try {
            defaultApi.login(loginRequest, { _ ->
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                // Save sessionId or proceed with next steps
            }, { error ->
                if (error.networkResponse != null) {
                    Log.e(TAG, "Login Error: ${error.networkResponse.statusCode}")
                    Toast.makeText(this, "Login Error: ${error.message}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Login Failed: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: ApiException) {
            e.printStackTrace()
            Toast.makeText(this, "API Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        private const val TAG = "MainActivity"
    }
}
