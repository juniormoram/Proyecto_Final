package com.example.proyecto_final


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

        lateinit var users: MutableList<CapaDatos.Usuario>
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        users = ArrayList()
        btn_Login.setOnClickListener {
            buscarUsuario()
        }
        lbl_NuevoUsuario.setOnClickListener{
        startActivity(Intent(this,RegistroActivity::class.java))
    }
    }
    // BUSQUEDA DE USUARIO EN TABLA "Usuario"

    fun buscarUsuario() {
        var index1 = 0
        val listaser2 = CapaDatos.SharedApp.prefs.name2

        // SE OBTIENEN LOS DATOS DE LA TABLA "Usuario"
        if(listaser2 != "") {
            val gson2 = GsonBuilder().create()
            val Model = gson2.fromJson(listaser2, Array<CapaDatos.Usuario>::class.java)
                .toMutableList()
            CapaDatos.SharedApp.listaUsuarios = Model
            Log.d("Logueo: ","Si hay usuarios")

        }else{
            Log.d("Logueo: ","No hay usuarios")
        }

        // SE BUSCA Y OBTIENE LA INFORMACIÓN DEL "Usuario" INDICADO EN EL INICIO DE SESIÓN
        for (item in CapaDatos.SharedApp.listaUsuarios){
            Log.d("Indice: ",index1.toString())
            if (CapaDatos.SharedApp.listaUsuarios[index1].Usuario == txt_Usuario.text.toString()){
                CapaDatos.SharedApp.UsuarioJ.Usuario = CapaDatos.SharedApp.listaUsuarios[index1].Usuario
                CapaDatos.SharedApp.UsuarioJ.Contraseña = CapaDatos.SharedApp.listaUsuarios[index1].Contraseña
                CapaDatos.SharedApp.UsuarioJ.Apellido = CapaDatos.SharedApp.listaUsuarios[index1].Apellido
                CapaDatos.SharedApp.UsuarioJ.Nombre = CapaDatos.SharedApp.listaUsuarios[index1].Nombre
                CapaDatos.SharedApp.UsuarioJ.Imagen = CapaDatos.SharedApp.listaUsuarios[index1].Imagen
                Log.d("Indice2: ",index1.toString())
            }
            index1= index1 + 1
        }
        doAsync {

            uiThread {
                Log.d("Logueo ok: ", CapaDatos.SharedApp.Usuario.toString())
                ValidCredenPref()
            }
        }
    }

    // SE VALIDAN LOS CREDENCIALES DEL USUARIO QUE INICIA SESIÓN
    fun ValidCredenPref(){
        val text = "Contraseña incorrecta"
        val text2 = "Usuario no existe"
        val duration = Toast.LENGTH_SHORT
        if (CapaDatos.SharedApp.UsuarioJ.Usuario == ""){
            Log.d("Usuario no existe: ",users.toString())
            val toast = Toast.makeText(applicationContext, text2, duration)
            toast.show()
        }else{
            if(CapaDatos.SharedApp.UsuarioJ.Contraseña == txt_Contrasena.text.toString()){
                CapaDatos.SharedApp.NomUsuario = CapaDatos.SharedApp.UsuarioJ.Nombre + " " + CapaDatos.SharedApp.UsuarioJ.Apellido
                startActivity(Intent(this,InicioActivity::class.java))
            }else{
                Log.d("Contraseña incorrecta: ", CapaDatos.SharedApp.UsuarioJ.toString())
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

        }

    }
}
