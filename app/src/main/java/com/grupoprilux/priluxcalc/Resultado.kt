package com.grupoprilux.priluxcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_resultado.*

class Resultado : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        //Creamos un extra para recibir los datos, se crea asi para en caso de que este vacio no falle
        val extras = intent.extras ?: return

        // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave
        val numeroLuxes = extras.getDouble("NUMEROLUXES")
        val numeroLuminarias = extras.getDouble("NUMEROLUMINARIAS")


        luxesLabel.text = numeroLuxes.toString()
        numeroLuminariasLabel.text = numeroLuminarias.toString()




    }
}
