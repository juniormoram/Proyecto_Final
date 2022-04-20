package com.example.proyecto_final

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaPersonasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_layout_lista)
        getPersonas()
    }
    private fun getPersonas(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofitPERSONA().create(RegistroFacturaActivity.APIServicePERSONA::class.java).registrationPost().execute()
            runOnUiThread {
                CapaDatos.SharedApp.emisores = call.body()!!
                Log.i("", call.body()!!.toString())
                Log.i("", CapaDatos.SharedApp.emisores.toString())
                setUpRecyclerViewEmisor()
            }
        }
    }
    lateinit var mRecyclerView : RecyclerView
    val mAdapterEmisor : adapter_emisor = adapter_emisor()
    fun setUpRecyclerViewEmisor(){

        mRecyclerView = findViewById(R.id.listapersonas) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapterEmisor.adapter_emisor(CapaDatos.SharedApp.emisores, this)
        mRecyclerView.adapter = mAdapterEmisor

    }
    private fun getRetrofitPERSONA(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://mariomep-001-site1.itempurl.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}