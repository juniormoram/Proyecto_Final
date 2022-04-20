package com.example.proyecto_final

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapter_emisor : RecyclerView.Adapter<adapter_emisor.ViewHolder>()  {

    var emisores:MutableList<CapaDatos.PERSONA> = ArrayList()

    lateinit var context: Context

    fun adapter_emisor(emisores: MutableList<CapaDatos.PERSONA>, context: Context){
        this.emisores = emisores
        this.context = context
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = emisores.get(position)
        holder.bind(item, context)
        holder.botonselecciona.setOnClickListener{
            val emisor: CapaDatos.PERSONA = emisores.get(position)
            onBtnClickListener(emisor)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_layout_emisor, parent, false))
    }

    override fun getItemCount(): Int {
        return emisores.size
    }

    private fun onBtnClickListener(emisor: CapaDatos.PERSONA){
        Toast.makeText(context, "persona: ${emisor.NOMBRE1}", Toast.LENGTH_LONG).show()
        RegistroFacturaActivity.SharedApp.emisorFactura = emisor
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombre = view.findViewById(R.id.nombre) as TextView
        val numero = view.findViewById(R.id.numero) as TextView
        val correo = view.findViewById(R.id.correo) as TextView
        val botonselecciona = view.findViewById(R.id.btnselecciona) as Button


        fun bind(emisor: CapaDatos.PERSONA, context: Context, ){
            nombre.text = emisor.NOMBRE1
            numero.text = emisor.NUMERO1
            correo.text= emisor.CORREOELECTRONICO1
        }
        fun ImageView.loadUrl(url: String) {
            Picasso.with(context).load(url).into(this)
        }
    }
}