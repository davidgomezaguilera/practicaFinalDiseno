package com.example.practicafinaldiseo

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafinaldiseo.models.Producto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.item_cesta.view.*
import kotlinx.android.synthetic.main.item_producto.view.*

class CestaAdapter(val cesta: MutableList<Producto>):RecyclerView.Adapter<CestaAdapter.cestaHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cestaHolder {

        var layoutInflater = LayoutInflater.from(parent.context)

        return cestaHolder(layoutInflater.inflate(R.layout.item_cesta,parent,false))
    }

    override fun onBindViewHolder(holder: cestaHolder, position: Int) {

        holder.render(cesta[position])

    }

    override fun getItemCount(): Int {
        return cesta.size
    }

    class cestaHolder(var view:View):RecyclerView.ViewHolder(view){


        fun render(producto:Producto){

            view.tvNombreProductoCesta.text = producto.nombre
            view.tvPrecioProductoCesta.text = producto.precio.toString()
            view.tvDescProductoCesta.text = producto.descripcion
            view.tvCantidad.text = producto.cantidad.toString()
            Picasso.get().load(producto.imagen).into(view.imagenCesta)
            var precioItem = view.tvPrecioProductoCesta.text.toString().toDouble()
            //var precioTotal = view.tvTotalPagar.text.toString().toDouble()
            //view.tvTotalPagar.text = (precioTotal + precioItem).toString()



        }

    }

}