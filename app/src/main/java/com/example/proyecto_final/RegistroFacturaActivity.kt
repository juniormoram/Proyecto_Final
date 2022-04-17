package com.example.proyecto_final

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class RegistroFacturaActivity : AppCompatActivity() {

    private val COUNTRIES = arrayOf(
        "Belgium", "France", "Italy", "Germany", "Spain"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_factura)

        val selectEmisor: Spinner = findViewById(R.id.emisorSelection)
        val selectReceptor: Spinner = findViewById(R.id.receptorSelection)
        //Agrega datos de forma dinamica al select, 
        //esto hay que cambiarlo una vez se obtengan los datos correspondientes del API
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            COUNTRIES
        )
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        selectEmisor.adapter = spinnerArrayAdapter
        selectReceptor.adapter = spinnerArrayAdapter
    }
}