package com.example.proyecto_final

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_crear_factura.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

            lateinit var diasdelmes: MutableList<FECHA>
            lateinit var emisores: MutableList<PERSONA>
        }

        override fun onCreate() {
            super.onCreate()
//guardamos una lista de la clase fecha, que nos debe devolver la peticion
            diasdelmes  = ArrayList()
            emisores  = ArrayList()
        }


    }

    data class UBICACION(
        @SerializedName("PROVINCIA1") var PROVINCIA1: String,
        @SerializedName("CANTON1") var CANTON1: String,
        @SerializedName("DISTRITO1") var DISTRITO1: String,
        @SerializedName("BARRIO1") var BARRIO1: String,
        @SerializedName("OTRASSENAS1") var OTRASSENAS1: String,
        @SerializedName("ID1") var ID1: String
    )
    data class TELEFONOFAX
        (
        @SerializedName("CODIGOPAIS1") var CODIGOPAIS1: String,
        @SerializedName("NUMTELEFONO1") var NUMTELEFONO1: String
        )
    data class PERSONA
        (
        @SerializedName("NOMBRE1") var NOMBRE1: String,
        @SerializedName("TIPO") var TIPO: String,
        @SerializedName("NUMERO1") var NUMERO1: String,
        @SerializedName("NOMBRECOMERCIAL1") var NOMBRECOMERCIAL1: String,
        @SerializedName("CORREOELECTRONICO1") var CORREOELECTRONICO1: String,
        @SerializedName("IDENTIFICACIONEXTRANJERO1") var IDENTIFICACIONEXTRANJERO1: String,
        @SerializedName("IDUBICACION1") var IDUBICACION1: UBICACION,
        @SerializedName("TELEFONO1") var TELEFONO1: TELEFONOFAX,
        @SerializedName("FAX1") var FAX1: TELEFONOFAX
        )

    data class EMPRESA(
        @SerializedName("CEDULAJURIDICA") var CEDULAJURIDICA: String,
        @SerializedName("DESPEDIDA") var DESPEDIDA: String,
        @SerializedName("DIRECCION") var DIRECCION: String,
        @SerializedName("DIRECCIONXY") var DIRECCIONXY: String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("IMAGENURL") var IMAGENURL: String,
        @SerializedName("IMAGENURLMOVIL") var IMAGENURLMOVIL: String,
        @SerializedName("IMPUESTOAPLICA") var IMPUESTOAPLICA: String,
        @SerializedName("NOMBRE") var NOMBRE: String,
        @SerializedName("PAGADO") var PAGADO: String,
        @SerializedName("SALUDO") var SALUDO: String,
        @SerializedName("TELEFONO1") var TELEFONO1: String,
        @SerializedName("TELEFONO2") var TELEFONO2: String,
        @SerializedName("URLESCENARIO") var URLESCENARIO: String
    )

    data class EVENTO(
        @SerializedName("DESCRIPCION") var DESCRIPCION: String,
        @SerializedName("DETALLE") var DETALLE: String,
        @SerializedName("EMPRESA") var EMPRESA: String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("IMAGENURL") var IMAGENURL: String,
        @SerializedName("IMPUESTO") var IMPUESTO: Number,
        @SerializedName("NOMBRE") var NOMBRE: String
    )
    data class LISTA_PRECIOS(

        @SerializedName("CODIGO_ACCESO") var CODIGO_ACCESO:String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("NOMBRE") var NOMBRE:String,
        @SerializedName("NUMERO") var NUMERO:Number,
        @SerializedName("TOTAL") var TOTAL:Number

    )

    //clase padre donde se utiliza relaciones de composicion con las clases anteriores para obtener una unica clase con los
    //atributos necesarios que devuelve la peticion
    data class FECHA(
        @SerializedName("ANNO") var ANNO: Number,
        @SerializedName("CERRADO") var CERRADO: String,
        @SerializedName("DIA") var DIA: Number,
        @SerializedName("EMPRESA") var EMPRESA: EMPRESA,
        @SerializedName("EVENTO") var EVENTO: EVENTO,
        @SerializedName("HORAS_CADUCA") var HORAS_CADUCA: Number,
        @SerializedName("HORA_FIN") var HORA_FIN:String,
        @SerializedName("HORA_INICIA") var HORA_INICIA:String,
        @SerializedName("ID") var ID: Number,
        @SerializedName("LISTA_PRECIOS") var LISTA_PRECIOS: LISTA_PRECIOS,
        @SerializedName("MES") var MES: Number,
        @SerializedName("NUMERO_HORA_FIN") var NUMERO_HORA_FIN: Number,
        @SerializedName("NUMERO_HORA_INICIA") var NUMERO_HORA_INICIA: Number,
        @SerializedName("NUMERO_MINUTO_FIN") var NUMERO_MINUTO_FIN: Number,
        @SerializedName("NUMERO_MINUTO_INICIA") var NUMERO_MINUTO_INICIA: Number,
        @SerializedName("TERMINAL") var TERMINAL:String
        )

//    lateinit var mRecyclerView : RecyclerView
//    val mAdapterEmisor : adapter_emisor = adapter_emisor()
    //funcion de carga del recycler view con la lista de fechas que se debe llamar al realizar la peticion
//    fun setUpRecyclerView(){
//
//
//        mRecyclerView = findViewById(R.id.listapersonas) as RecyclerView
//        mRecyclerView.setHasFixedSize(true)
//        mRecyclerView.layoutManager = LinearLayoutManager(this)
//        mAdapter.RecyclerAdapter(RegistroFacturaActivity.SharedApp.diasdelmes, this)
//        mRecyclerView.adapter = mAdapter
//
//    }

    //funcion de carga del recycler view con la lista de fechas que se debe llamar al realizar la peticion
//    fun setUpRecyclerViewEmisor(){
//
//        mRecyclerView = findViewById(R.id.listapersonas) as RecyclerView
//        mRecyclerView.setHasFixedSize(true)
//        mRecyclerView.layoutManager = LinearLayoutManager(this)
//        mAdapterEmisor.adapter_emisor(RegistroFacturaActivity.SharedApp.emisores, this)
//        mRecyclerView.adapter = mAdapterEmisor
//
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        btn_obras.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val call = getRetrofit().create(APIService::class.java).registrationPost("listarparamovil","11","2021").execute()
//                // Log.d("",call.body().toString())
//                //  val puppies = call.body() as String?
//                runOnUiThread {
//                    SharedApp.diasdelmes = call.body()!!
//                    Log.i("", SharedApp.diasdelmes.toString())
//                    //Log.d("",puppies.toString())
//                    setUpRecyclerView()
//                    // Log.d("",call.body().toString())
//                }
//            }}
//
//        BTN_PERSONAS.setOnClickListener {
//
//            CoroutineScope(Dispatchers.IO).launch {
//                val call = getRetrofitPERSONA().create(APIServicePERSONA::class.java).registrationPost().execute()
//                // Log.d("",call.body().toString())
//                //  val puppies = call.body() as String?
//                runOnUiThread {
//                    SharedApp.emisores = call.body()!!
//                    Log.i("", call.body()!!.toString())
//                    Log.i("", SharedApp.emisores.toString())
//                    setUpRecyclerViewEmisor()
//                }
//            }
//
//        }
//    }

    interface APIService {
        @POST("Calendario")
        @FormUrlEncoded
        fun registrationPost(
            @Field("op") op: String,
            @Field("mes") mes: String,
            @Field("anno") anno: String
        ): Call<MutableList<FECHA>>
    }

    interface APIServicePERSONA {
        @GET("PERSONA")
        //FUNCION SIN PARÁMETROS, GET NO RECIBE PARÁMETROS
        fun registrationPost(
        ): Call<MutableList<PERSONA>> //IMPORTANTE EL GET DEVUELVE UNA LISTA DE LA CLASE RESPECTIVA
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://teatros.sistemcr.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}