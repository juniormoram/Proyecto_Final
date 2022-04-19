package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_crear_factura.*


class RegistroFacturaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_factura)

        emisorBtn.setOnClickListener{
            startActivity(Intent(this,RegistroFacturaActivity::class.java))
        }
        receptorBtn.setOnClickListener{
            startActivity(Intent(this,RegistroFacturaActivity::class.java))
        }

    }
}