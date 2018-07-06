package com.grupoprilux.priluxcalc.Calculo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.grupoprilux.priluxcalc.R
import com.grupoprilux.priluxcalc.Resultados.Resultado
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
    var apertura : Double = 0.0
    var lumenes = 0.0
    var areaRetornada : Double = 0.0
    var alturaRetornada : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculos)


        val extras = intent.extras ?: return



        // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave
        lumenes = extras.getDouble("LUMENESLUMINARIA")
        distanciaSuelo = extras.getDouble("DISTACIONSUELONORMATIVA")
        luxes = extras.getDouble("LUXESNORMATIVA")
        apertura = extras.getDouble("APERTURALUMINANCIA")

        val nombreNormativa = extras.getString("NOMBRENORMATIVA")

        if (nombreNormativa != "Calculo Libre") {

            lumenesEditText.visibility = View.INVISIBLE
            lumenesTextView.visibility = View.INVISIBLE
            distanciaSueloEditText.visibility = View.INVISIBLE
            distanciaSueloTextView.visibility = View.INVISIBLE

        }

        distanciaSueloEditText.text = Editable.Factory.getInstance().newEditable(distanciaSuelo.toString())
        lumenesEditText.text = Editable.Factory.getInstance().newEditable(lumenes.toString())

      //  startActivityForResult(intent,100)
    }


    fun botonCalcular(view: View) {


        Log.e("TAG","ALTURA: $alto, ANCHO: $ancho , LARGO: $largo, LUXES: $luxes, DISTANCIA: $distanciaSuelo" )

        //comprobamos que los campos no esten vacios
        if(comprobarCamposVacios() == true) {
            Log.e ("TAG","HAY CAMPOS VACIOS")
        } else {

            alto = java.lang.Double.parseDouble(alturaEditText.getText().toString())
            ancho = java.lang.Double.parseDouble(anchoEditText.getText().toString())
            largo = java.lang.Double.parseDouble(largoEditText.getText().toString())
            luxes = java.lang.Double.parseDouble(lumenesEditText.getText().toString())
            distanciaSuelo = java.lang.Double.parseDouble(distanciaSueloEditText.getText().toString())

            var calcularLuxes = calcularLuxes()
            var numeroLuminarias = calcularNumeroLuminarias()


            Log.e("TAG", "Numero de luminarias: $numeroLuminarias")
            Log.e("TAG", "Numero de luxes: $calcularLuxes")

            var intent = Intent(this, Resultado::class.java)

            //Con esto mandamos informacion primitiva
            intent.putExtra("NUMEROLUMINARIAS", numeroLuminarias)
            intent.putExtra("NUMEROLUXES", calcularLuxes)


            //Esto envia la informacion siempre es lo ultimo que se hace
            startActivityForResult(intent,100)
            //startActivity(intent)

        }

    }

    fun calcularLuxes() :Double {

        val grados = calcularGrados( apertura)
        val radianes = calcularRadianes(grados)
        val radio = calcularRadio(alto,radianes)
        areaIluminanciaPorLuminaria = pow(radio, 2.0)
        Log.e("TAG","AreaPorLuminaria: $areaIluminanciaPorLuminaria")
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

    fun comprobarCamposVacios() : Boolean {

        if (alturaEditText.text.isEmpty() == true) {

            Toast.makeText(this,"Campo Alto vacio, introduce un valor",Toast.LENGTH_LONG).show()

            return true
        }
        if (anchoEditText.text.isEmpty() == true) {

            Toast.makeText(this,"Campo Ancho vacio, introduce un valor",Toast.LENGTH_LONG).show()

            return true
        }
        if (largoEditText.text.isEmpty() == true) {


            Toast.makeText(this,"Campo Largo vacio, introduce un valor",Toast.LENGTH_LONG).show()

            return true
        }

        if (lumenesEditText.text.isEmpty() == true) {

            Toast.makeText(this,"Campo Lumenes vacio, introduce un valor",Toast.LENGTH_LONG).show()

            return true
        }
        if (distanciaSueloEditText.text.isEmpty() == true){

            Toast.makeText(this,"Campo Distancia al Suelo vacio, introduce un valor",Toast.LENGTH_LONG).show()

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

                var areaRetornada = data!!.hasExtra("AREADEVUELTA").toString()
                var alturaRetornada = data!!.hasExtra("ALTURADEVUELTA").toString()

                areaIluminanciaPorLuminaria = areaRetornada.toDouble()
                alturaEditText.text = Editable.Factory.getInstance().newEditable(alturaRetornada)

                Log.e("TAG",areaRetornada)
                Log.e("TAG",alturaRetornada)
            }
        }





    }


}
