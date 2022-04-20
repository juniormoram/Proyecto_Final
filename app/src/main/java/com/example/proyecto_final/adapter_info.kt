package com.example.proyecto_final

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapter_info : RecyclerView.Adapter<adapter_info.ViewHolder>()  {
    var infos:MutableList<CapaDatos.INFORMACIONREFERENCIA> = ArrayList()

    lateinit var context: Context

    fun adapter_info(infos: MutableList<CapaDatos.INFORMACIONREFERENCIA>, context: Context){
        this.infos = infos
        this.context = context

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = infos.get(position)
        holder.bind(item, context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_layout_info, parent, false))
    }

    override fun getItemCount(): Int {
        return infos.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tipo = view.findViewById(R.id.tipo) as TextView
        val razon = view.findViewById(R.id.razon) as TextView
        val codigo = view.findViewById(R.id.codigo) as TextView
        val botonselecciona = view.findViewById(R.id.btnselecciona) as Button

        fun bind(info: CapaDatos.INFORMACIONREFERENCIA, context: Context){
            tipo.text = info.TIPODOC1
            razon.text = info.RAZON1
            codigo.text= info.CODIGO1
        }

        fun ImageView.loadUrl(url: String) {
            Picasso.with(context).load(url).into(this)
        }
    }
}