package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.activity_lineas_detalle.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaDetallesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lineas_detalle)
        getDetailInfoLines()
        selectBtn.setOnClickListener{
            startActivity(Intent(this,RegistroFacturaActivity::class.java))
            Log.i("","Detalles seleccionados: ${RegistroFacturaActivity.SharedApp.lineasDetalleSelected}")
        }
    }
    fun getDetailInfoLines(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofitDetailLines().create(RegistroFacturaActivity.APIServiceDetailsINFO::class.java).registrationPost().execute()
            runOnUiThread {
                RegistroFacturaActivity.SharedApp.details = call.body()!!
                Log.i("", RegistroFacturaActivity.SharedApp.details.toString())
                setUpRecyclerViewInfo()
            }
        }
    }
    lateinit var mRecyclerView : RecyclerView
    val mAdapterInfo : adapterLineInfo = adapterLineInfo()

    fun setUpRecyclerViewInfo(){
        mRecyclerView = findViewById<RecyclerView>(R.id.detailLinesID)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapterInfo.adapterLineInfo(RegistroFacturaActivity.SharedApp.details, this)
        mRecyclerView.adapter = mAdapterInfo

    }
    private fun getRetrofitDetailLines(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://mariomep-001-site1.itempurl.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}