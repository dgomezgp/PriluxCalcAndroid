package com.grupoprilux.priluxcalc

import com.google.gson.annotations.SerializedName

/**
 * Created by davidgomez on 23/5/18.

class Luminaria (nombre: String, lumenes: Double, apertura: Double) {

    //Propiedades de la clase
    var nombre  : String = nombre
    var lumenes: Double = lumenes
    var apertura: Double = apertura


}




class Luminaria {

    var TIPO : String? = null
    var CODIGO : String? = null
    var CORTO : String? = null
    var NOMBRE : String? = null
    var LUMENES : String? = null
    var APERTURA : String? = null
    var FOTO : String? = null
}


class Luminaria(
        @SerializedName("TIPO") val tipo: String,
        @SerializedName("CODIGO") val codigo: String,
        @SerializedName("CORTO") val corto: String,
        @SerializedName("NOMBRE") val nombre: String,
        @SerializedName("APERTURA") val apertura: String,
        @SerializedName("LUMENES") val lumenes: String,
        @SerializedName("FOTO") val foto: String
)
*/

data class Luminaria (

        val tipo: String,
        val codigo: String,
        val corto: String,
        val nombre: String,
        val lumenes: String,
        val apertura: String,
        val foto: String

)