package com.grupoprilux.priluxcalc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

class Eleccion : AppCompatActivity() {

    var lumenes = ""
    var nombreLuminaria = ""
    var apertura = ""
    var nombreNormativa = ""
    var luxesNormativa = ""
    var alturaSueloNormativa = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleccion)

        val extras = intent.extras

        //Compruebo si el extra viene vacio y evito error
        if (extras == null) {
            Log.e("TAGELECCIONNULL", "El EXTRA esta null")
        } else {

            // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave
            lumenes = extras.getString("LUMENESLUMINARIA")
            nombreLuminaria = extras.getString("NOMBRELUMINARIA")
            apertura = extras.getString("APERTURALUMINARIA")
            nombreNormativa = extras.getString("NOMBRENORMATIVA")
            luxesNormativa = extras.getString("LUXESNORMATIVA")
            alturaSueloNormativa = extras.getString("ALTURASUELONORMATIVA")

            Log.e("TAGELECCION", "LUMENES: $lumenes NOMBRELUMI: $nombreLuminaria APERTURA: $apertura NOMBRENORMA: $nombreNormativa  LUXESNORMA: $luxesNormativa  ALTURASUELO: $alturaSueloNormativa")

        }
    }

    fun botonUnidades(view: View) {
        var intent = Intent(this, Calculos::class.java)

        intent.putExtra("NOMBRENORMATIVA", nombreNormativa)
        intent.putExtra("LUXESNORMATIVA", luxesNormativa)
        intent.putExtra("ALTURASUELONORMATIVA", alturaSueloNormativa)
        intent.putExtra("LUMENESLUMINARIA", lumenes)
        intent.putExtra("APERTURALUMINARIA", apertura)
        intent.putExtra("NOMBRELUMINARIA", nombreLuminaria)

        this.startActivity(intent)
    }

    fun botonLuxes(view: View) {
        var intent = Intent(this, CalculoLuxes::class.java)

        intent.putExtra("NOMBRENORMATIVA", nombreNormativa)
        intent.putExtra("LUXESNORMATIVA", luxesNormativa)
        intent.putExtra("ALTURASUELONORMATIVA", alturaSueloNormativa)
        intent.putExtra("LUMENESLUMINARIA", lumenes)
        intent.putExtra("APERTURALUMINARIA", apertura)
        intent.putExtra("NOMBRELUMINARIA", nombreLuminaria)

        this.startActivity(intent)
    }

    //Infla el menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu2,menu)
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
    fun showInfoAlert(view: Eleccion) {
        val dialog = AlertDialog.Builder(this).setTitle("AYUDA").setMessage("Elige que deseas calcular: \n -Unidades: Muestra el número de unidades necesarias para iluminar el lugar. \n -Luxes: Muestra el numero de luxes a razón del número de puntos de luz")
                .setPositiveButton("OK", { dialog, i ->
                })
        dialog.show()
    }
}
