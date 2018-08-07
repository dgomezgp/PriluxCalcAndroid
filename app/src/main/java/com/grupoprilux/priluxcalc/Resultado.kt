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
import kotlin.math.round
import kotlin.math.sqrt
import kotlin.math.tan

class Resultado : AppCompatActivity() {

    var numeroLuxes: Double = 0.0
    var nombreLuminaria: String = ""
    var luxesNormativa: Double = 0.0
    var nombreNormativa: String = ""
    var lumenesLuminaria: String = ""
    var areaCorregidaParaEnviar: Double = 0.0
    var numeroLuminarias: Double = 0.0
    var alturaCorregidaParaEnviar: Double = 0.0
    var numeroLuxesDouble: Double = 0.0
    var luxesNormativaDouble: Double = 0.0
    var lumenesLuminariaDouble: Double = 0.0
    var aperturaLuminariaDouble: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        //Creamos un extra para recibir los datos, se crea asi para en caso de que este vacio no falle
        val extras = intent.extras ?: return

        // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave

        numeroLuminarias = extras.getDouble("NUMEROLUMINARIAS", numeroLuminarias)
        numeroLuxes = extras.getDouble("NUMEROLUXES", numeroLuxes)
        nombreLuminaria = extras.getString("NOMBRELUMINARIA", nombreLuminaria)
        luxesNormativaDouble = extras.getDouble("LUXESNORMATIVA", luxesNormativa)
        aperturaLuminariaDouble = extras.getDouble("APERTURALUMINARIA")
        lumenesLuminariaDouble = extras.getDouble("LUMENESLUMINARIA")
        nombreNormativa = extras.getString("NOMBRENORMATIVA")

        if (nombreNormativa == "LIBRE") {
            resultadoLuxesLabel.text = numeroLuxes.toInt().toString()
            val numeroRedondeado = redondeo(numeroLuminarias).toInt()
            resultadoNumeroLuminariasLabel.text = numeroRedondeado.toString()
            recalcularBoton.visibility = View.INVISIBLE
        } else {
            Log.e("TAG", "Luxes Recibidos: $numeroLuxes tiene que ser mayor que $luxesNormativa ")
            if (numeroLuxes < luxesNormativaDouble) {
                recalcularBoton.visibility = View.VISIBLE
                luxesLabel.text = " El resultado no cumple la normativa vigente"
                resultadoLuxesLabel.visibility = View.INVISIBLE
                resultadoNumeroLuminariasLabel.text = " Por favor pulsa el boton recalcular"
                numeroLuminariasLabel.visibility = View.INVISIBLE
            } else {
                resultadoLuxesLabel.text = numeroLuxes.toInt().toString()
                val numeroRedondeado = redondeo(numeroLuminarias).toInt()
                resultadoNumeroLuminariasLabel.text = numeroRedondeado.toString()
                recalcularBoton.visibility = View.INVISIBLE
            }
        }


    }


    //realiza los calculos en el caso de que no cumpla normativa y se los pasa al activity anterior
    fun recalcular(view: View) {
        Log.e("TAG", "Luxes normativa: $luxesNormativaDouble")
        Log.e("TAG", "Lumenes luminaria: $lumenesLuminariaDouble")
        val area = lumenesLuminariaDouble / luxesNormativaDouble
        areaCorregidaParaEnviar = area
        print("Area: $area")
        val radio = sqrt(area / PI)
        print("Radio: $radio")
        val alturaCorregidaParaEnviar = radio / tan((((aperturaLuminariaDouble / 2) / 360) * 2) * PI)
        print("Altura para el recalculo:  $alturaCorregidaParaEnviar")

        val infoADevolver = Intent()
        infoADevolver.putExtra("AREADEVUELTA", areaCorregidaParaEnviar)
        infoADevolver.putExtra("ALTURADEVUELTA", alturaCorregidaParaEnviar)

        //Indicamos si ha funcionado
        //El parametro RESULT_OK se escribe tal cual
        setResult(RESULT_OK, infoADevolver)

        super.finish()

    }

    //Redondea el resultado y en caso de que redonde a la baja suma uno para redondear a la alta
    fun redondeo(numero: Double): Double {

        var resultado = round(numero)

        if (resultado < numero) {
            resultado = numero + 1
            round(resultado)
        }
        Log.e("TAGREDONDEO", "$resultado")
        return resultado
    }

    //Infla el menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu2, menu)
        return true
    }

    //Llama a la funcion de ayuda (showInfoAlert
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
    fun showInfoAlert(view: Resultado) {
        val dialog = AlertDialog.Builder(this).setTitle("AYUDA").setMessage("Muestra el resultado del calculo.\n Si tu eleccion no cumple la normativa pulsa el boton Recalcular y automaticamente te corregira la altura a la que debe estar la luminaria para poder cumplir la normativa vigente.")
                .setPositiveButton("OK", { dialog, i ->
                })
        dialog.show()
    }
}
