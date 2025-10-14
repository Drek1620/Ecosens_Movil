package com.example.ecosens_movil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecosens_movil.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // Usa el calificador para evitar ambigüedades con extensiones
        this.setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etCorreo.text?.toString().orEmpty()
            val pass  = binding.etPassword.text?.toString().orEmpty()
            Snackbar.make(binding.root, "Login: $email", Snackbar.LENGTH_SHORT).show()
        }
    }
}
