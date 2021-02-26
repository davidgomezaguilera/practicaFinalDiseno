package com.example.practicafinaldiseo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicafinaldiseo.models.Producto
import com.example.practicafinaldiseo.ui.dashboard.DashboardFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_producto.view.*
import java.io.Serializable
import java.net.URL

class ProductoAdapter(val productos:List<Producto>):RecyclerView.Adapter<ProductoAdapter.productoHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productoHolder {

        val layoutInflater = LayoutInflater.from(parent.context)


        return productoHolder(layoutInflater.inflate(R.layout.item_producto,parent, false))
    }
//soy la posicion POSITION, coge a mi holder
    override fun onBindViewHolder(holder: productoHolder, position: Int) {

        holder.render(productos[position])

    }
    /*fun ImageView.loadUrl(url:String){
        Glide.with(context).load(url).into(this)
    }*/
    override fun getItemCount(): Int {
        return productos.size
    }

    class productoHolder(val view:View):RecyclerView.ViewHolder(view){

        private lateinit var database: DatabaseReference
        private lateinit var firebaseAuth: FirebaseAuth
        var cesta:MutableList<Producto> = ArrayList<Producto>()
        fun render(producto:Producto){
            view.tvNombreProducto.text = producto.nombre
            view.tvDescProducto.text = producto.descripcion
            view.tvPrecioProducto.text = producto.precio.toString()

            Picasso.get().load(producto.imagen).placeholder(R.drawable.common_google_signin_btn_icon_dark).into(view.imagenProducto)

            //Glide.with(view.context).load(producto.imagen).error(R.drawable.common_google_signin_btn_icon_dark).placeholder(R.drawable.com_facebook_close).into(view.imagenProducto)
            view.btAddProducto.setOnClickListener {
                //aqui debemos añadir este produco a una lista que despues mostraremos en la cesta.
                var usuario = Firebase.auth.currentUser
                database = Firebase.database.reference
               if(cesta.contains(producto)){
                    producto.cantidad++
                    cesta.add(producto)
                }else{
                    cesta.add(producto)
                }
                database.child("Usuarios").child(usuario?.uid.toString()).child("cesta").child(producto.nombre).setValue(producto)

                //var producto = Producto(producto.nombre,producto.descripcion,producto.precio,producto.imagen,producto.cantidad)


                //cesta.add(Producto(producto.nombre,producto.descripcion,producto.precio,producto.imagen))

            }
        }

    }


}