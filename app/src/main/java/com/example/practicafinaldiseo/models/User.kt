package com.example.practicafinaldiseo.models

class User() {

     var uid = ""
     var nombre = ""
     var email = ""
     var provider = ""
     var cesta = arrayListOf<Producto>()



     constructor(nombre:String,email:String, provider:String) : this() {
         this.nombre = nombre
         this.email = email
         this.provider = provider
     }



}