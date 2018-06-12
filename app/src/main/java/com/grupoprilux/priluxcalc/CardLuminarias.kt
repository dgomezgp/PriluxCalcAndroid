package com.grupoprilux.priluxcalc


import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_card_luminarias.*
import kotlinx.android.synthetic.main.content_main_luminarias.*

class CardLuminarias : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.VistaPepe>? = null




    override fun onCreate(savedInstanceState: Bundle?) {

        var objeto1 = Luminaria("Bombilla", 20.0, 10.0)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_luminarias)
        //setSupportActionBar(toolbar)

        //Definimos un LayoutManager
        layoutManager = LinearLayoutManager(this)
        //Le asignamos a nuestro Recycler View el LayoutManager
        recycler_view.layoutManager = layoutManager

        //Definimos un RecyclerAdapter
        adapter = RecyclerAdapter()
        //Le asignamos a nuestro Recycler su Adapter
        recycler_view.adapter = adapter

        collapsing_toolbar.setCollapsedTitleTextColor(Color.BLUE)
        collapsing_toolbar.title = "Luminarias"
    }

}
