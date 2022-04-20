package com.example.proyecto_final

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_crear_factura.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


class RegistroFacturaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_factura)

        emisorBtn.setOnClickListener{
            startActivity(Intent(this,ListaPersonasActivity::class.java))
        }
        receptorBtn.setOnClickListener{
            startActivity(Intent(this,ListaPersonasActivity::class.java))
        }

    }

    class SharedApp : Application() {
        companion object {

            lateinit var diasdelmes: MutableList<CapaDatos.FECHA>
            lateinit var emisores: MutableList<CapaDatos.PERSONA>
            lateinit var receptores: MutableList<CapaDatos.PERSONA>
        }

        override fun onCreate() {
            super.onCreate()
//guardamos una lista de la clase fecha, que nos debe devolver la peticion
            diasdelmes  = ArrayList()
            emisores  = ArrayList()
            receptores = ArrayList()
        }


    }
    interface APIService {
        @POST("Calendario")
        @FormUrlEncoded
        fun registrationPost(
            @Field("op") op: String,
            @Field("mes") mes: String,
            @Field("anno") anno: String
        ): Call<MutableList<CapaDatos.FECHA>>
    }

    interface APIServicePERSONA {
        @GET("PERSONA")
        //FUNCION SIN PARÁMETROS, GET NO RECIBE PARÁMETROS
        fun registrationPost(
        ): Call<MutableList<CapaDatos.PERSONA>> //IMPORTANTE EL GET DEVUELVE UNA LISTA DE LA CLASE RESPECTIVA
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://teatros.sistemcr.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}