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

class ListaInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_lista_info)
        getInfos()
    }
    fun getInfos(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofitINFO().create(RegistroFacturaActivity.APIServiceINFO::class.java).registrationPost().execute()
            runOnUiThread {
                RegistroFacturaActivity.SharedApp.infos = call.body()!!
                Log.i("", call.body()!!.toString())
                Log.i("", RegistroFacturaActivity.SharedApp.infos.toString())
                setUpRecyclerViewInfo()
            }
        }
    }
    lateinit var mRecyclerView : RecyclerView
    val mAdapterInfo : adapter_info = adapter_info()
    fun setUpRecyclerViewInfo(){

        mRecyclerView = findViewById(R.id.listainfo) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapterInfo.adapter_info(RegistroFacturaActivity.SharedApp.infos, this)
        mRecyclerView.adapter = mAdapterInfo

    }
    private fun getRetrofitINFO(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://mariomep-001-site1.itempurl.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}