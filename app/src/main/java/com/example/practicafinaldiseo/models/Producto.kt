package com.example.practicafinaldiseo.models

import android.graphics.Bitmap

class Producto {

    var nombre:String
    var descripcion:String
    var precio:Double
    var imagen:String
    var cantidad:Int

    constructor(nombre:String, desc:String, precio:Double, imagen:String, cantidad:Int){
        this.nombre = nombre
        this.descripcion = desc
        this.precio = precio
        this.imagen = imagen
        this.cantidad = cantidad
    }

}