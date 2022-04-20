package com.example.proyecto_final

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapterLineInfo : RecyclerView.Adapter<adapterLineInfo.ViewHolder>()  {

    var details:MutableList<CapaDatos.LINEADETALLE> = ArrayList()

    lateinit var context: Context

    fun adapterLineInfo(details: MutableList<CapaDatos.LINEADETALLE>, context: Context){
        this.details = details
        this.context = context
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = details.get(position)
        holder.bind(item, context)
        holder.checkBoxS.setOnClickListener {
            onClickListener(item, holder)
        }
    }
    fun onClickListener(lineaDetalle : CapaDatos.LINEADETALLE, view: ViewHolder) {
        val checkBoxIsSelected = view.checkBoxS.isSelected
        view.checkBoxS.isSelected = !checkBoxIsSelected
        var isSelected = -1
        if(RegistroFacturaActivity.SharedApp.lineasDetalleSelected.size > 0){
            isSelected = RegistroFacturaActivity.SharedApp.lineasDetalleSelected.indexOf(lineaDetalle)
        }
        if(isSelected == -1){
            RegistroFacturaActivity.SharedApp.lineasDetalleSelected.add(lineaDetalle)
        } else{
            RegistroFacturaActivity.SharedApp.lineasDetalleSelected.remove(lineaDetalle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_layout_lineas_detalles, parent, false))
    }

    override fun getItemCount(): Int {
        return details.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val detalle = view.findViewById(R.id.txtLineaDetalle) as TextView
        val checkBoxS = view.findViewById(R.id.checkBoxSelection) as CheckBox


        fun bind(details: CapaDatos.LINEADETALLE, context: Context, ){
            detalle.text = "Cod: ${details.CODIGO1} Detalle: ${details.DETALLE1} Precio unitario: ${details.PRECIOUNITARIO_1}"
        }

        fun ImageView.loadUrl(url: String) {
            Picasso.with(context).load(url).into(this)
        }
    }
}