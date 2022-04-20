package com.example.proyecto_final

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaReceptorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_lista_receptor)
        getPersonas()
    }
    fun getPersonas(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofitPERSONA().create(RegistroFacturaActivity.APIServicePERSONA::class.java).registrationPost().execute()
            runOnUiThread {
                RegistroFacturaActivity.SharedApp.receptores = call.body()!!
                Log.i("", call.body()!!.toString())
                Log.i("", RegistroFacturaActivity.SharedApp.receptores.toString())
                setUpRecyclerViewReceptor()
            }
        }
    }
    lateinit var mRecyclerView : RecyclerView
    val mAdapterReceptor : adapter_receptor = adapter_receptor()
    fun setUpRecyclerViewReceptor(){

        mRecyclerView = findViewById(R.id.listapersonas) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapterReceptor.adapter_receptor(RegistroFacturaActivity.SharedApp.receptores, this)
        mRecyclerView.adapter = mAdapterReceptor

    }
    private fun getRetrofitPERSONA(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://mariomep-001-site1.itempurl.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}