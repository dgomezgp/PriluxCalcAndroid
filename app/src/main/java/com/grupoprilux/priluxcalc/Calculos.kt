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
import kotlinx.android.synthetic.main.activity_calculos.*
import java.lang.Math.pow
import kotlin.math.PI

class Calculos : AppCompatActivity() {

    var alto: Double = 0.0
    var ancho: Double = 0.0
    var largo: Double = 0.0
    var luxes: String = ""
    var distanciaSuelo: String = ""
    var areaIluminanciaPorLuminaria: Double = 0.0
    var apertura: String = ""
    var lumenes = ""
    var nombreNormativa = ""
    var nombreLuminaria = ""
    var mantenimiento = 0.9
    var areaRetornada: Double = 0.0
    var alturaRetornada: Double = 0.0
    var distanciaSueloDouble = 0.0
    var lumenesLuminariaDouble = 0.0
    var aperturaLuminariaDouble = 0.0
    var luxesNormativaDouble = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculos)


        val extras = intent.extras ?: return


        // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave
        lumenes = extras.getString("LUMENESLUMINARIA")
        nombreLuminaria = extras.getString("NOMBRELUMINARIA")
        distanciaSuelo = extras.getString("ALTURASUELONORMATIVA")
        luxes = extras.getString("LUXESNORMATIVA")
        apertura = extras.getString("APERTURALUMINARIA")
        nombreNormativa = extras.getString("NOMBRENORMATIVA")

        distanciaSueloDouble = distanciaSuelo.toDouble()
        luxesNormativaDouble = luxes.toDouble()
        lumenesLuminariaDouble = lumenes.toDouble()
        aperturaLuminariaDouble = apertura.toDouble()

        if (nombreNormativa != "LIBRE") {

            lumenesEditText.visibility = View.INVISIBLE
            lumenesTextView.visibility = View.INVISIBLE
            distanciaSueloEditText.visibility = View.INVISIBLE
            distanciaSueloTextView.visibility = View.INVISIBLE

        }

        distanciaSueloEditText.text = Editable.Factory.getInstance().newEditable(distanciaSuelo.toString())
        lumenesEditText.text = Editable.Factory.getInstance().newEditable(lumenes.toString())


    }


    fun botonCalcular(view: View) {


        Log.e("TAG", "ALTURA: $alto, ANCHO: $ancho , LARGO: $largo, LUXES: $luxes, DISTANCIA: $distanciaSuelo")

        //comprobamos que los campos no esten vacios
        if (comprobarCamposVacios() == true) {
            Log.e("TAG", "HAY CAMPOS VACIOS")
        } else {

            alto = java.lang.Double.parseDouble(alturaEditText.getText().toString())
            ancho = java.lang.Double.parseDouble(anchoEditText.getText().toString())
            largo = java.lang.Double.parseDouble(largoEditText.getText().toString())


            var calcularLuxes = calcularLuxes() * Kfactor() * mantenimiento
            var numeroLuminarias = calcularNumeroLuminarias()


            Log.e("TAG", "Numero de luminarias: $numeroLuminarias")
            Log.e("TAG", "Numero de luxes: $calcularLuxes")

            var intent = Intent(this, Resultado::class.java)

            //Con esto mandamos informacion primitiva
            intent.putExtra("NUMEROLUMINARIAS", numeroLuminarias)
            intent.putExtra("NUMEROLUXES", calcularLuxes)
            intent.putExtra("NOMBRELUMINARIA", nombreLuminaria)
            intent.putExtra("LUXESNORMATIVA", luxesNormativaDouble)
            intent.putExtra("LUMENESLUMINARIA", lumenesLuminariaDouble)
            intent.putExtra("APERTURALUMINARIA", aperturaLuminariaDouble)
            intent.putExtra("NOMBRENORMATIVA", nombreNormativa)

            //Esto envia la informacion siempre es lo ultimo que se hace
            startActivityForResult(intent, 100)


        }

    }

    fun calcularLuxes(): Double {

        val grados = calcularGrados(aperturaLuminariaDouble)
        val radianes = calcularRadianes(grados)
        val radio = calcularRadio(alto, radianes)
        areaIluminanciaPorLuminaria = pow(radio, 2.0)
        Log.e("TAG", "AreaPorLuminaria: $areaIluminanciaPorLuminaria")
        val resultadoSuelo = lumenesLuminariaDouble / areaIluminanciaPorLuminaria

        return resultadoSuelo
    }


    fun calcularNumeroLuminarias(): Double {

        val resultado = (ancho * largo) / areaIluminanciaPorLuminaria

        return resultado
    }


    fun calcularGrados(apertura: Double): Double {

        val resultado = apertura / 2
        Log.e("TAG", "Grados: $resultado")

        return resultado
    }

    fun calcularRadianes(grados: Double): Double {
        var resultado: Double
        val calculo = 2 * PI
        val calculo2 = calculo / 360.0
        resultado = calculo2 * grados
        Log.e("TAG", "Radianes: $resultado")

        return resultado
    }


    fun calcularRadio(altura: Double, radinanes: Double): Double {

        var resultado = Math.tan(radinanes) * (altura - distanciaSueloDouble)
        Log.e("TAG", "Radio: $resultado")

        return resultado

    }

    fun Kfactor(): Double {

        val ancho = java.lang.Double.parseDouble(anchoEditText.getText().toString())
        val largo = java.lang.Double.parseDouble(largoEditText.getText().toString())
        val altura = java.lang.Double.parseDouble(alturaEditText.getText().toString())
        val alturaSuelo = java.lang.Double.parseDouble(distanciaSueloEditText.getText().toString())
        val resultado = ((ancho * largo) / ((altura - alturaSuelo) * (largo + ancho)))
        return resultado
    }

    fun comprobarCamposVacios(): Boolean {

        if (alturaEditText.text.isEmpty() == true) {

            Toast.makeText(this, "Campo Altura vacio, introduce un valor", Toast.LENGTH_LONG).show()

            return true
        }
        if (alturaEditText.text.toString() == "0") {

            Toast.makeText(this, "El campo Altura debe ser mayor que 0", Toast.LENGTH_LONG).show()

            return true
        }
        if (anchoEditText.text.isEmpty() == true) {

            Toast.makeText(this, "Campo Ancho vacio, introduce un valor", Toast.LENGTH_LONG).show()

            return true
        }
        if (anchoEditText.text.toString() == "0") {

            Toast.makeText(this, "El campo Ancho debe ser mayor que 0", Toast.LENGTH_LONG).show()

            return true
        }
        if (largoEditText.text.isEmpty() == true) {


            Toast.makeText(this, "Campo Largo vacio, introduce un valor", Toast.LENGTH_LONG).show()

            return true
        }

        if (largoEditText.text.toString() == "0") {

            Toast.makeText(this, "El campo Largo debe ser mayor que 0", Toast.LENGTH_LONG).show()

            return true
        }

        if (lumenesEditText.text.isEmpty() == true) {

            Toast.makeText(this, "Campo Lumenes vacio, introduce un valor", Toast.LENGTH_LONG).show()

            return true
        }

        if (lumenesEditText.text.toString() == "0") {

            Toast.makeText(this, "El campo Lumenes debe ser mayor que 0", Toast.LENGTH_LONG).show()

            return true
        }
        if (distanciaSueloEditText.text.isEmpty() == true) {

            Toast.makeText(this, "Campo Distancia al Suelo vacio, introduce un valor", Toast.LENGTH_LONG).show()

            return true
        }

        var alturaConver: Double = java.lang.Double.parseDouble(alturaEditText.getText().toString())
        var distanciaSueloConver: Double = java.lang.Double.parseDouble(distanciaSueloEditText.getText().toString())

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
                    alturaEditText.text = Editable.Factory.getInstance().newEditable(alturaRetornadaConver.toString())

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
    fun showInfoAlert(view: Calculos) {
        val dialog = AlertDialog.Builder(this).setTitle("AYUDA").setMessage("Introduce los datos solicitados para realizar el calculo")
                .setPositiveButton("OK", { dialog, i ->
                })
        dialog.show()
    }

}
