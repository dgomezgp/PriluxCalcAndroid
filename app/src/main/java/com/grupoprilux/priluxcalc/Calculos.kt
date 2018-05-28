package com.grupoprilux.priluxcalc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_calculos.*
import java.lang.Math.pow
import kotlin.math.PI

class Calculos : AppCompatActivity() {

    var alto : Double = 0.0
    var ancho : Double = 0.0
    var largo : Double = 0.0
    var luxes : Double = 0.0
    var distanciaSuelo : Double = 0.0
    var areaIluminanciaPorLuminaria : Double = 0.0
    var apertura : Double = 120.0
    var luminariaARecibir = 10250.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculos)
    }


    fun botonCalcular(view: View) {

        alto = java.lang.Double.parseDouble(alturaEditText.getText().toString())
        ancho = java.lang.Double.parseDouble(anchoEditText.getText().toString())
        largo = java.lang.Double.parseDouble(largoEditText.getText().toString())
        luxes = java.lang.Double.parseDouble(lumenesEditText.getText().toString())
        distanciaSuelo = java.lang.Double.parseDouble(distanciaSueloEditText.getText().toString())

        Log.e("TAG","ALTURA: $alto, ANCHO: $ancho , LARGO: $largo, LUXES: $luxes, DISTANCIA: $distanciaSuelo" )


         var calcularLuxes = calcularLuxes()
         var numeroLuminarias = calcularNumeroLuminarias()

        Log.e("TAG","Numero de luminarias: $numeroLuminarias")
        Log.e("TAG","Numero de luxes: $calcularLuxes")

        var intent = Intent(this, Resultado::class.java)
        //Con esto mandamos informacion primitiva
        intent.putExtra("NUMEROLUMINARIAS",numeroLuminarias)
        intent.putExtra("NUMEROLUXES",calcularLuxes)

        //Esto envia la informacion siempre es lo ultimo que se hace
        startActivity(intent)

    }

    fun calcularLuxes() :Double {

        val grados = calcularGrados( apertura)
        val radianes = calcularRadianes(grados)
        val radio = calcularRadio(alto,radianes)
        areaIluminanciaPorLuminaria = pow(radio, 2.0)
        Log.e("TAG","AreaPorLuminaria: $areaIluminanciaPorLuminaria")
        val lumenes = luminariaARecibir
        val resultadoSuelo = lumenes / areaIluminanciaPorLuminaria

        return resultadoSuelo
    }


    fun calcularNumeroLuminarias() : Double {

        val resultado = (ancho * largo) / areaIluminanciaPorLuminaria

        return resultado
    }


    fun calcularGrados(apertura: Double) : Double {

        val resultado = apertura / 2
        Log.e("TAG","Grados: $resultado")

        return resultado
    }

    fun calcularRadianes(grados: Double) : Double {
        var resultado: Double
        val calculo = 2 * PI
        val calculo2 = calculo / 360.0
        resultado = calculo2 * grados
        Log.e("TAG","Radianes: $resultado")

        return resultado
    }


    fun calcularRadio ( altura: Double,radinanes: Double) : Double {

        var resultado = Math.tan(radinanes) * (altura - distanciaSuelo)
        Log.e("TAG","Radio: $resultado")

        return resultado

    }



}
