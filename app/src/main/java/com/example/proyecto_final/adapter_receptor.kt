package com.example.proyecto_final

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapter_receptor : RecyclerView.Adapter<adapter_receptor.ViewHolder>()  {
    var receptores:MutableList<CapaDatos.PERSONA> = ArrayList()

    lateinit var context: Context

    fun adapter_receptor(receptores: MutableList<CapaDatos.PERSONA>, context: Context){
        this.receptores = receptores
        this.context = context

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = receptores.get(position)
        holder.bind(item, context)
        holder.botonselecciona.setOnClickListener{
            val receptor: CapaDatos.PERSONA = receptores.get(position)
            onBtnClickListener(receptor)
        }
    }
    private fun onBtnClickListener(receptor: CapaDatos.PERSONA){
        Toast.makeText(context, "persona: ${receptor.NOMBRE1}", Toast.LENGTH_LONG).show()
        CapaDatos.SharedApp.receptorFactura = receptor
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_layout_receptor, parent, false))
    }

    override fun getItemCount(): Int {
        return receptores.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombre = view.findViewById(R.id.nombre) as TextView
        val numero = view.findViewById(R.id.numero) as TextView
        val correo = view.findViewById(R.id.correo) as TextView
        val botonselecciona = view.findViewById(R.id.btnselecciona) as Button


        fun bind(receptor: CapaDatos.PERSONA, context: Context){
            nombre.text = receptor.NOMBRE1
            numero.text = receptor.NUMERO1
            correo.text= receptor.CORREOELECTRONICO1
        }
        fun ImageView.loadUrl(url: String) {
            Picasso.with(context).load(url).into(this)
        }
    }
}