package com.grupoprilux.priluxcalc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_resultado.*
import kotlin.math.PI
import kotlin.math.sqrt
import kotlin.math.tan

class ResultadoLuxes : AppCompatActivity() {

    var numeroLuxes: Double = 0.0
    var numeroLuminarias: Double = 0.0
    var luxesNormativa = ""
    var nombreNormativa: String = ""
    var lumenesLuminaria = ""
    var areaCorregidaParaEnviar: Double = 0.0
    var aperturaLuminaria = ""
    var alturaCorregidaParaEnviar: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        //Creamos un extra para recibir los datos, se crea asi para en caso de que este vacio no falle
        val extras = intent.extras ?: return

        // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave
        numeroLuxes = extras.getDouble("NUMEROLUXES")
        numeroLuminarias = extras.getDouble("NUMEROLUMINARIAS")
        luxesNormativa = extras.getString("LUXESNORMATIVA")
        nombreNormativa = extras.getString("NOMBRENORMATIVA")
        lumenesLuminaria = extras.getString("LUMENESLUMINARIA")
        aperturaLuminaria = extras.getString("APERTURALUMINARIA")


        if (nombreNormativa == "LIBRE") {
            resultadoLuxesLabel.text = numeroLuxes.toInt().toString()
            resultadoNumeroLuminariasLabel.text = numeroLuminarias.toInt().toString()
            recalcularBoton.visibility = View.INVISIBLE
        } else {
            Log.e("TAG", "Luxes Recibidos: $numeroLuxes tiene que ser mayor que $luxesNormativa ")
            if (numeroLuxes < luxesNormativa.toDouble()) {
                recalcularBoton.visibility = View.VISIBLE
                luxesLabel.text = " El resultado no cumple la normativa vigente"
                resultadoLuxesLabel.visibility = View.INVISIBLE
                resultadoNumeroLuminariasLabel.text = " Por favor pulsa el boton recalcular"
                numeroLuminariasLabel.visibility = View.INVISIBLE
            } else {
                resultadoLuxesLabel.text = numeroLuxes.toInt().toString()
                resultadoNumeroLuminariasLabel.text = numeroLuminarias.toInt().toString()
                recalcularBoton.visibility = View.INVISIBLE
            }
        }


    }


    //realiza los calculos en el caso de que no cumpla normativa
    fun recalcular(view: View) {
        Log.e("TAG", "Luxes normativa: $luxesNormativa")
        Log.e("TAG", "Lumenes luminaria: $lumenesLuminaria")
        val area = lumenesLuminaria.toDouble() / luxesNormativa.toDouble()
        areaCorregidaParaEnviar = area
        print("Area: $area")
        val radio = sqrt(area / PI)
        print("Radio: $radio")
        val alturaCorregidaParaEnviar = radio / tan((((aperturaLuminaria.toDouble() / 2) / 360) * 2) * PI)
        print("Altura para el recalculo:  $alturaCorregidaParaEnviar")
        val infoADevolver = Intent()
        infoADevolver.putExtra("AREADEVUELTA", areaCorregidaParaEnviar)
        infoADevolver.putExtra("ALTURADEVUELTA", alturaCorregidaParaEnviar)

        //Indicamos si ha funcionado
        //El parametro RESULT_OK se escribe tal cual
        setResult(RESULT_OK, infoADevolver)

        super.finish()
    }

    //Infla el menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu)
        return true
    }

    //Llama a la funcion de ayuda (showInfoAlert)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.add_phrase -> {
                showInfoAlert(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    //Llama a un alert para mostrar ayuda
    fun showInfoAlert(view: ResultadoLuxes) {
        val dialog = AlertDialog.Builder(this).setTitle("AYUDA").setMessage("Muestra el resultado del calculo.\n Si tu eleccion no cumple la normativa pulsa el boton Recalcular y automaticamente te corregira la altura a la que debe estar la luminaria para poder cumplir la normativa vigente.")
                .setPositiveButton("OK", { dialog, i ->
                })
        dialog.show()
    }
}
