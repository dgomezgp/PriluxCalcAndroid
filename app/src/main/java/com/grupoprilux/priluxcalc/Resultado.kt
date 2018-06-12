package com.grupoprilux.priluxcalc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_resultado.*
import kotlin.math.PI
import kotlin.math.sqrt
import kotlin.math.tan

class Resultado : AppCompatActivity() {

    var numeroLuxes : Double = 0.0
    var numeroLuminarias : Double = 0.0
    var luxesNormativa : Double = 0.0
    var nombreNormativa : String = ""
    var lumenesLuminaria : Double = 0.0
    var areaCorregidaParaEnviar : Double = 0.0
    var aperturaLuminaria : Double = 0.0
    var alturaCorregidaParaEnviar : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        //Creamos un extra para recibir los datos, se crea asi para en caso de que este vacio no falle
        val extras = intent.extras ?: return

        // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave
        numeroLuxes = extras.getDouble("NUMEROLUXES")
        numeroLuminarias = extras.getDouble("NUMEROLUMINARIAS")
        luxesNormativa = extras.getDouble("LUXESNORMATIVA")
        nombreNormativa = extras.getString("NOMBRENORMATIVA")
        lumenesLuminaria = extras.getDouble("LUMENESLUMINARIA")
        aperturaLuminaria = extras.getDouble("APERTURALUMINRIA")


        if (nombreNormativa == "Calculo Libre") {
            resultadoLuxesLabel.text = luxesNormativa.toString()
            resultadoNumeroLuminariasLabel.text = lumenesLuminaria.toString()
            recalcularBoton.visibility = View.INVISIBLE
        } else {
            Log.e ("TAG","Luxes Recibidos: $numeroLuxes tiene que ser mayor que $luxesNormativa ")
            if (numeroLuxes < luxesNormativa) {
                recalcularBoton.visibility = View.INVISIBLE
                alturaCorregidaParaEnviar = recalcular()
                luxesLabel.text = " El resultado no cumple la normativa vigente"
                resultadoLuxesLabel.text = " Por favor pulsa el boton recalcular"
                resultadoNumeroLuminariasLabel.visibility = View.INVISIBLE
                numeroLuminariasLabel.visibility = View.INVISIBLE
            } else {
                resultadoLuxesLabel.text = numeroLuxes.toString()
                resultadoNumeroLuminariasLabel.text = numeroLuminarias.toString()
                recalcularBoton.visibility = View.INVISIBLE
            }
        }


    }


    //realiza los calculos en el caso de que no cumpla normativa
    fun recalcular () : Double{
        Log.e( "TAG", "Luxes normativa: $luxesNormativa")
        Log.e("TAG","Lumenes luminaria: $lumenesLuminaria")
        val area = lumenesLuminaria / luxesNormativa
        areaCorregidaParaEnviar = area
        print ("Area: $area")
        val radio =  sqrt(area / PI)
        print ("Radio: $radio")
        val resultado = radio / tan((((aperturaLuminaria/2) / 360) * 2 ) * PI)
        print("Altura para el recalculo:  $resultado")
        return resultado
    }


    // Esto es para pasar un valor a un activity anterior
    // Para ello teneos que sobreescribir el metodo finish()
    override fun finish() {
        val infoADevolver = Intent()
        infoADevolver.putExtra("AREADEVUELTA", areaCorregidaParaEnviar)
        infoADevolver.putExtra("ALTURADEVUELTA", alturaCorregidaParaEnviar)

        //Indicamos si ha funcionado
        //El parametro RESULT_OK se escribe tal cual
        setResult(RESULT_OK,infoADevolver)

        super.finish()

    }


}
