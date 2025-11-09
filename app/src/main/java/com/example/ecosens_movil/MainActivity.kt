package com.example.ecosens_movil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ecosens_movil.R
import com.example.ecosens_movil.repo.LoginRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progress: ProgressBar
    private val repo = LoginRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmail = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progress = findViewById(R.id.progressBar)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingresa email y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            doLogin(email, password)
        }
    }

    private fun doLogin(email: String, password: String) {
        progress.visibility = android.view.View.VISIBLE
        btnLogin.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repo.login(email, password)
                }

                progress.visibility = android.view.View.GONE
                btnLogin.isEnabled = true

                if (response.token.isNotEmpty()) {
                    // Guarda toda la información del usuario
                    val prefs = getSharedPreferences("ecosens_prefs", Context.MODE_PRIVATE)
                    prefs.edit().apply {
                        putString("auth_token", response.token)
                        putInt("user_id", response.userId)
                        putInt("tipo_id", response.tipoId)
                        putString("user_email", email)
                        apply()
                    }

                    Toast.makeText(this@MainActivity, "Login exitoso", Toast.LENGTH_SHORT).show()

                    // Navega a HomeActivity
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Error en login", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                progress.visibility = android.view.View.GONE
                btnLogin.isEnabled = true
                Toast.makeText(this@MainActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
