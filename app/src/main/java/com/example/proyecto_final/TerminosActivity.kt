package com.example.proyecto_final

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_termcond.*

class TerminosActivity: AppCompatActivity() {

    var terminos : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termcond)
        terminos = "Los términos y condiciones de la siguiente aplicación corresponden a un sistema de facturación" +
                "Al acceder o consultar este programa, usted acepta los términos y condiciones del mismo\n" +

                "1.\t Sobre la aceptación de los términos y condiciones  \n" +
                "El usuario debe aceptar los términos y condiciones.\n" +
                "Son de caracter obligatorio\n" +
                "Para aceptar los mismos, debe seleccionar la opción de téminos y condiciones en el registro al programa.\n" +
                "\n"
        lbl_ter.movementMethod = ScrollingMovementMethod()
        lbl_ter.setText(terminos.toString())

    }
}