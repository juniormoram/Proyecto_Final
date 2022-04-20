package com.example.proyecto_final

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_crear_factura.*
import kotlinx.android.synthetic.main.activity_form_factura.*
import kotlinx.android.synthetic.main.activity_inicio.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FacturaFormActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_factura)
        val decodedByte = Base64.decode(CapaDatos.SharedApp.UsuarioJ.Imagen, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)
        editFEmision.setText(formatted)
        editReferencia.setText(RegistroFacturaActivity.SharedApp.infoFactura.RAZON1)
        editReferencia2.setText(RegistroFacturaActivity.SharedApp.infoFactura.TIPODOC1)
        editEmisor.setText(RegistroFacturaActivity.SharedApp.emisorFactura.NOMBRE1)
        editEmisor2.setText(RegistroFacturaActivity.SharedApp.emisorFactura.NUMERO1)
        editReceptor.setText(RegistroFacturaActivity.SharedApp.receptorFactura.NOMBRE1)
        editReceptor2.setText(RegistroFacturaActivity.SharedApp.receptorFactura.NUMERO1)
        //editd1
        //editd2
        //editd3
        //editd4
    }
}