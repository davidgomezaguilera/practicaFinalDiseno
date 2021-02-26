package com.example.practicafinaldiseo.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafinaldiseo.CestaAdapter
import com.example.practicafinaldiseo.ProductoAdapter
import com.example.practicafinaldiseo.R
import com.example.practicafinaldiseo.models.Producto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.item_cesta.view.*
import java.lang.NumberFormatException

private lateinit var database: DatabaseReference
private val cesta:MutableList<Producto> = ArrayList<Producto>()
private var precioTotal:Double = 0.0
class DashboardFragment : Fragment() {


    var descripcion = ""
    var imagen = ""
    var nombre = ""
    var precio = ""
    var cantidad = 1
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        var recyclerCesta:RecyclerView = root.findViewById(R.id.recyclerCesta)
        recyclerCesta.layoutManager = LinearLayoutManager(context)

        //println("Cesta 0 "+ cesta[0].nombre)

        cargarArray()
        println("Precio Total  "+ precioTotal)
        root.tvTotalPagar.text = precioTotal.toString()
        val adapter = CestaAdapter(cesta)
        recyclerCesta.setAdapter(adapter)

        return root
    }

    fun cargarArray(){
        var nombre =""
        var desc=""
        var precio= 0.0
        var imagen=""

        //precioTotal = 0.0
        database = Firebase.database.reference
        var usuario = Firebase.auth.currentUser
        var referencia = database.child("Usuarios").child(usuario?.uid.toString()).child("cesta")



            referencia.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //EL MAYOR PROBLEMA ES QUE NO ENCUENTRA HIJOS DE LA REFERENCIA ENTONCES NO PUEDO CARGAR EL ARRAY PERO TODO LO DEMAS ESTA BIEN
                    cesta.clear()
                    for (snap in snapshot.children) {
                        for (valor in snap.children) {
                            if (valor.key.equals("descripcion")) {
                                descripcion = valor.value.toString()
                            }
                            if (valor.key.equals("nombre")) {
                                nombre = valor.value.toString()
                            }
                            if (valor.key.equals("precio")) {
                                precio = valor.value.toString().toDouble()
                            }
                            if (valor.key.equals("imagen")) {
                                imagen = valor.value.toString()
                            }
                            if( valor.key.equals("cantidad")){
                                cantidad = valor.value.toString().toInt()
                                println("Cantidad "+ cantidad)
                            }
                        }
                        try {
                            var add = Producto(nombre, desc, precio, imagen,cantidad)

                            println("Precio Total 2 "+precioTotal )
                            if(cesta.contains(add)){
                                    println("Lo contieneeeee")
                                    //cesta.clear()


                                //add.cantidad ++
                                    cesta.add(add)
                                }else{

                                    //add.cantidad = add.cantidad
                                    precioTotal += add.precio
                                    cesta.add(add)
                                }

                            //cesta.add(add)
                            if (cesta.contains(add)) {
                                //cesta.remove(add)

                                println("LO CONTIENE DEL CONTAINS")
                                //cesta.add(add)
                                //add.cantidad = 2
                                //cesta.add(add)
                            } else {


                            }

                        } catch (e: NumberFormatException) {
                            println(e.message)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    println("ON CANCELLEEEEEEED")
                }

            })

















    /*var database = Firebase.database.reference
        var usuario = Firebase.auth.currentUser
        var ref = database.child("Usuarios").child(usuario?.uid.toString()).child("cesta")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for(producto in snapshot.children){
                    println("Producto value "+producto.value.toString())
                    for(hijos in producto.children){

                        if(hijos.key.equals("descripcion")){
                            descripcion = hijos.value.toString()
                            println("Descripcion "+ descripcion)
                        }else if(hijos.key.equals("imagen")){
                            imagen = hijos.value.toString()
                            println("Imagen "+ imagen)
                        }else if(hijos.key.equals("nombre")){
                            nombre = hijos.value.toString()
                            println("Nombre "+ nombre)
                        }else if(hijos.key.equals("precio")){
                            precio = hijos.value.toString()
                            println("Precio "+ precio)
                        }

                    }

                }
                cesta.add(Producto(nombre,descripcion,0.0,imagen))
                println("Tamño de la lista despues justo de añadir "+cesta.size)
                for(p in cesta){
                    println("Nombre producto dentro de la cesta " +p.nombre)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })*/
    }
}