package com.example.ecosens_movil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var tvUserName: TextView
    private lateinit var ivUserAvatar: ImageView
    private lateinit var ivSettings: ImageView
    private lateinit var bottomNavigation: BottomNavigationView

    private var tipoId: Int = 0 // 1 = Admin, 2 = Recolector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Obtener datos del usuario
        val prefs = getSharedPreferences("ecosens_prefs", Context.MODE_PRIVATE)
        val userEmail = prefs.getString("user_email", "Usuario")
        tipoId = prefs.getInt("tipo_id", 1)

        // Inicializar vistas
        tvUserName = findViewById(R.id.tvUserName)
        ivUserAvatar = findViewById(R.id.ivUserAvatar)
        ivSettings = findViewById(R.id.ivSettings)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Configurar header
        tvUserName.text = userEmail?.substringBefore("@") ?: "Usuario"

        // Configurar menú según el rol
        setupBottomNavigation()

        // Menú de configuración - IMPORTANTE: usar setOnClickListener
        ivSettings.setOnClickListener { view ->
            showSettingsMenu(view)
        }

        // Cargar fragmento inicial
        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.menu.clear()

        if (tipoId == 1) {
            // Menú para Administrador
            bottomNavigation.inflateMenu(R.menu.menu_bottom_admin)
        } else {
            // Menú para Recolector
            bottomNavigation.inflateMenu(R.menu.menu_bottom_recolector)
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    loadFragment(DashboardFragment())
                    true
                }
                R.id.nav_sectores -> {
                    loadFragment(SectoresFragment())
                    true
                }
                R.id.nav_personal -> {
                    loadFragment(PersonalFragment())
                    true
                }
                R.id.nav_area -> {
                    loadFragment(AreaFragment())
                    true
                }
                R.id.nav_config -> {
                    loadFragment(ConfigFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun showSettingsMenu(view: View) {
        try {
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.menu_user_settings, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_profile -> {
                        // Abrir configuración de usuario
                        bottomNavigation.selectedItemId = R.id.nav_config
                        loadFragment(ConfigFragment())
                        true
                    }
                    R.id.action_logout -> {
                        showLogoutDialog()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        } catch (e: Exception) {
            e.printStackTrace()
            // Si falla el PopupMenu, mostrar diálogo directamente
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                logout()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun logout() {
        val prefs = getSharedPreferences("ecosens_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}