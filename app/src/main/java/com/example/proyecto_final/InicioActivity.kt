package com.example.proyecto_final

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_inicio.*

class InicioActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        btn_Crear_Factura.setOnClickListener{
            startActivity(Intent(this,RegistroFacturaActivity::class.java))
        }
        val decodedByte = Base64.decode(CapaDatos.SharedApp.UsuarioJ.Imagen, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        imageViewUsua.setImageBitmap(bitmap)
        lbl_Usuario.setText(CapaDatos.SharedApp.NomUsuario)
    }
}