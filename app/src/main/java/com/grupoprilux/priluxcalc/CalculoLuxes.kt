package com.grupoprilux.priluxcalc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculo_luxes.*
import kotlinx.android.synthetic.main.activity_calculos.*
import java.lang.Math.pow
import kotlin.math.PI

class CalculoLuxes : AppCompatActivity() {

    var alto: Double = 0.0
    var ancho: Double = 0.0
    var largo: Double = 0.0
    var luxes = ""
    var distanciaSuelo = ""
    var areaIluminanciaPorLuminaria: Double = 0.0
    var nombreNormativa = ""
    var lumenes = ""
    var mantenimiento = 0.9
    var puntosLuz = 0.0
    var aperturaLuminaria = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculo_luxes)


        val extras = intent.extras ?: return


        // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave
        lumenes = extras.getString("LUMENESLUMINARIA")
        distanciaSuelo = extras.getString("ALTURASUELONORMATIVA")
        luxes = extras.getString("LUXESNORMATIVA")
        nombreNormativa = extras.getString("NOMBRENORMATIVA")
        aperturaLuminaria = extras.getString("APERTURALUMINARIA")

        //asignamos le valor recibido al campo distancia suelo

        distanciaSueloLuxesEditText.setText(distanciaSuelo)


    }


    fun botonCalcular(view: View) {


        Log.e("TAG", "ALTURA: $alto, ANCHO: $ancho , LARGO: $largo, LUXES: $luxes, DISTANCIA: $distanciaSuelo")

        //comprobamos que los campos no esten vacios
        if (comprobarCamposVacios() == true) {
            Log.e("TAG", "HAY CAMPOS VACIOS")
        } else {

            alto = java.lang.Double.parseDouble(alturaLuxesEditText.getText().toString())
            ancho = java.lang.Double.parseDouble(anchoLuxesEditText.getText().toString())
            largo = java.lang.Double.parseDouble(largoLuxesEditText.getText().toString())
            puntosLuz = java.lang.Double.parseDouble(puntosDeLuzEditText.getText().toString())
            distanciaSuelo = distanciaSueloLuxesEditText.hint.toString()

            var calcularLuxes = calcularLuxes() * Kfactor() * mantenimiento




            Log.e("TAG", "Numero de luxes: $calcularLuxes")

            var intent = Intent(this, ResultadoLuxes::class.java)

            //Con esto mandamos informacion primitiva

            intent.putExtra("NUMEROLUXES", calcularLuxes)
            intent.putExtra("NUMEROLUMINARIAS", puntosLuz)
            intent.putExtra("NOMBRENORMATIVA", nombreNormativa)
            intent.putExtra("LUXESNORMATIVA", luxes)
            intent.putExtra("LUMENESLUMINARIA", lumenes)
            intent.putExtra("APERTURALUMINARIA", aperturaLuminaria)

            //Esto envia la informacion siempre es lo ultimo que se hace
            startActivityForResult(intent, 100)


        }

    }


    fun calcularLuxes(): Double {

        val ancho = java.lang.Double.parseDouble(anchoLuxesEditText.getText().toString())
        val largo = java.lang.Double.parseDouble(largoLuxesEditText.getText().toString())
        puntosLuz = java.lang.Double.parseDouble(puntosDeLuzEditText.getText().toString())


        val resultado = (puntosLuz * lumenes.toDouble()) / (ancho * largo)
        return resultado
    }


    fun Kfactor(): Double {

        val ancho = java.lang.Double.parseDouble(anchoLuxesEditText.getText().toString())
        val largo = java.lang.Double.parseDouble(largoLuxesEditText.getText().toString())
        val altura = java.lang.Double.parseDouble(alturaLuxesEditText.getText().toString())
        val alturaSuelo = java.lang.Double.parseDouble(distanciaSueloLuxesEditText.getText().toString())
        val resultado = ((ancho * largo) / ((altura - alturaSuelo) * (largo + ancho)))
        return resultado
    }

    fun comprobarCamposVacios(): Boolean {

        if (alturaLuxesEditText.text.isEmpty() == true) {

            Toast.makeText(this, "Campo Altura vacio, introduce un valor", Toast.LENGTH_LONG).show()

            return true
        }
        if (alturaLuxesEditText.text.toString() == "0") {

            Toast.makeText(this, "El campo Altura debe ser mayor que 0", Toast.LENGTH_LONG).show()

            return true
        }
        if (anchoLuxesEditText.text.isEmpty() == true) {

            Toast.makeText(this, "Campo Ancho vacio, introduce un valor", Toast.LENGTH_LONG).show()

            return true
        }
        if (anchoLuxesEditText.text.toString() == "0") {

            Toast.makeText(this, "El campo Ancho debe ser mayor que 0", Toast.LENGTH_LONG).show()

            return true
        }
        if (largoLuxesEditText.text.isEmpty() == true) {


            Toast.makeText(this, "Campo Largo vacio, introduce un valor", Toast.LENGTH_LONG).show()

            return true
        }
        if (largoLuxesEditText.text.toString() == "0") {

            Toast.makeText(this, "El campo Largo debe ser mayor que 0", Toast.LENGTH_LONG).show()

            return true
        }
        if (puntosDeLuzEditText.text.isEmpty() == true) {

            Toast.makeText(this, "Campo Puntos de Luz vacio, introduce un valor", Toast.LENGTH_LONG).show()

            return true
        }
        if (alturaLuxesEditText.text.toString() == "0") {

            Toast.makeText(this, "El campo Puntos de Luz debe ser mayor que 0", Toast.LENGTH_LONG).show()

            return true
        }
        if (distanciaSueloLuxesEditText.text.isEmpty() == true) {

            Toast.makeText(this, "Campo Distancia al Suelo vacio, introduce un valor", Toast.LENGTH_LONG).show()

            return true
        }

        var alturaConver: Double = java.lang.Double.parseDouble(alturaLuxesEditText.getText().toString())
        var distanciaSueloConver: Double = java.lang.Double.parseDouble(distanciaSueloLuxesEditText.getText().toString())

        if (alturaConver <= distanciaSueloConver) {

            Toast.makeText(this, "Campo Distancia al Suelo tiene que ser menor que el campo Altura", Toast.LENGTH_LONG).show()

            return true
        }

        return false

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //3.1 comprobamos que el request code coincide cone l que hemos usado en paso 1


        if (requestCode == 100) {
            //3.2 Comprobamos que el resultCode == RESULT_OK
            if (resultCode == RESULT_OK) {
                //3.3 Extraemos de data el extra que queremos
                // En este proceso es recomendable comprobar que exista
                // Con esta funcion compreuba si el intent contiene la claveABuscar

                var areaRetornada = data!!.hasExtra("AREADEVUELTA")
                var alturaRetornada = data!!.hasExtra("ALTURADEVUELTA")
                if (areaRetornada == true && alturaRetornada == true) {
                    areaIluminanciaPorLuminaria = data!!.extras.getDouble("AREADEVUELTA")
                    val alturaRetornadaConver = data!!.extras.getDouble("ALTURADEVUELTA")
                    alturaLuxesEditText.text = Editable.Factory.getInstance().newEditable(alturaRetornadaConver.toString())

                    Log.e("TAG", areaIluminanciaPorLuminaria.toString())
                    Log.e("TAG", alturaRetornadaConver.toString())
                }
            }
        }

    }

    //Infla el menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu2, menu)
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
    fun showInfoAlert(view: CalculoLuxes) {
        val dialog = AlertDialog.Builder(this).setTitle("AYUDA").setMessage("Introduce los datos solicitados para realizar el calculo")
                .setPositiveButton("OK", { dialog, i ->
                })
        dialog.show()
    }

}
