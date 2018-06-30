

package com.grupoprilux.priluxcalc


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_card_normativa.*
import kotlinx.android.synthetic.main.content_main_normativa.*

class CardNormativa : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterNormativa.VistaPepe2>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_normativa)
        //setSupportActionBar(toolbar)

        //Definimos un LayoutManager
        layoutManager = LinearLayoutManager(this)
        //Le asignamos a nuestro Recycler View el LayoutManager
        recycler_view.layoutManager = layoutManager

        //Definimos un RecyclerAdapter
        adapter = RecyclerAdapterNormativa()
        //Le asignamos a nuestro Recycler su Adapter
        recycler_view.adapter = adapter

        collapsing_toolbar.setCollapsedTitleTextColor(Color.BLUE)
        collapsing_toolbar.title = "Normativas"




        val extras = intent.extras ?: return



        // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave


        var apertura = extras.getDouble("APERTURALUMINARIA")
        var lumenes = extras.getDouble("LUMENESLUMINARIA")


        var intent = Intent(this, RecyclerAdapterNormativa::class.java)


        //Con esto mandamos informacion primitiva

        intent.putExtra("LUMENESLUMINARIA",lumenes)
        intent.putExtra("APERTURALUMINARIA",apertura)

        //Esto envia la informacion siempre es lo ultimo que se hace
        startActivity(intent)



    }

}


