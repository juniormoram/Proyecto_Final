package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_inicio.*

class InicioActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        btn_Crear_Factura.setOnClickListener{
            startActivity(Intent(this,RegistroFacturaActivity::class.java))
        }
    }

}