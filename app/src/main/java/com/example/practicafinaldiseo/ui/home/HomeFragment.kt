package com.example.practicafinaldiseo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafinaldiseo.ProductoAdapter
import com.example.practicafinaldiseo.R
import com.example.practicafinaldiseo.models.Producto
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

//    private lateinit var homeViewModel: HomeViewModel
    //val listaProductos = arrayListOf<Producto>()
    val listaProductos:List<Producto> = listOf(

        Producto("Cóctel Aguacate","Cóctel compuesto de aguacate al puro estilo tropical",10.50,"https://i.pinimg.com/originals/fb/d3/d1/fbd3d1858befa8c1d9c931a9992285e2.jpg",1),
        Producto("Arroz Carabineros","Rico arroz con carabineros",15.50,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcREPcqBSZ9ReQ0pO-EAK7XWEhHYE2gaKHuREA&usqp=CAU",1),
        Producto("Carrilleras","Carrilleras al vino tinto con patatas al puente",20.50,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJmaL03nQhRbIPuC-sT0aueoO9hWbbj1K5lw&usqp=CAU",1)


    )
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //homeViewModel =
          //      ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerProductos: RecyclerView = root.findViewById(R.id.recyclerProductos)

        recyclerProductos.layoutManager = LinearLayoutManager(context)
        val adapter = ProductoAdapter(listaProductos)
        recyclerProductos.setAdapter(adapter)

        return root
    }


}