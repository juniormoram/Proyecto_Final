package com.example.proyecto_final

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.room.*
import com.google.gson.annotations.SerializedName

class CapaDatos {

    class Prefs (context: Context)
    {
        val PREFS_NAME = "com.cursokotlin.sharedpreferences"
        val SHARED_NAME2 = ""
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

        var name2: String?
            get() = prefs.getString(SHARED_NAME2, "")
            set(value) = prefs.edit().putString(SHARED_NAME2, value).apply()
    }

    class SharedApp : Application() {
        companion object {
            lateinit var prefs: Prefs
            lateinit var UbicacionUsu: UbicacionUsuario
            lateinit var Foto : String
            lateinit var NomUsuario: String
            lateinit var Usuario : Usuario
            lateinit var UsuarioJ : Usuario
            lateinit var listaUsuarios : MutableList<Usuario>
            var image_uri: Uri? = null

        }

        override fun onCreate() {
            super.onCreate()
            prefs = Prefs(applicationContext)
            listaUsuarios = ArrayList()
            Usuario = Usuario("","","","","")
            UsuarioJ = Usuario("","","","","")
            NomUsuario = ""


        }
    }

    data class UbicacionUsuario(

        var ejex:String,
        var ejey:String,
    )

    data class Usuario (
        @SerializedName("Usuario") var Usuario: String,
        @SerializedName("Nombre") var Nombre: String,
        @SerializedName("Apellido") var Apellido: String,
        @SerializedName("Contraseña") var Contraseña:  String,
        @SerializedName("Imagen") var Imagen:  String
    )


}