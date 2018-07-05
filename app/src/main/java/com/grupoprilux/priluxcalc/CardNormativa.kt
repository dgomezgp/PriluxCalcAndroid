

package com.grupoprilux.priluxcalc

/*
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
//        startActivity(intent)



    }

}

*/


import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_card_luminarias.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class CardNormativa : AppCompatActivity() {


    var nombre : String = ""
    var lumenes : Double = 0.0
    var apertura : Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_normativa)



        val url = "http:www.grupoprilux.com/priluxcalc/normativa.php"
        AsyncTaskHandleJSON().execute(url)

        val extras = intent.extras ?: return



        // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave
        lumenes = extras.getDouble("LUMENESLUMINARIA")
        nombre = extras.getString("NOMBRELUMINARIA")
        apertura = extras.getDouble("APERTURALUMINANCIA")

        Log.i("TAG","$nombre****$lumenes++++$apertura")

    }

    inner class AsyncTaskHandleJSON: AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {

            var texto: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                texto = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }
            return texto
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJSON(result)
        }
    }

    private fun handleJSON(jsonString: String?) {


        //{"NOMBRE":"LIBRE","LUXES":"0","ALTSUELO":"0"}

        val jsonArray = JSONArray(jsonString)
        var list = ArrayList<Normativa>()
        var objetoNum = 0

        while(objetoNum < jsonArray.length()) {
            val priluxJsonObjet = jsonArray.getJSONObject(objetoNum)
            list.add(Normativa(
                    priluxJsonObjet.getString("NOMBRE"),
                    priluxJsonObjet.getString("LUXES"),
                    priluxJsonObjet.getString("ALTSUELO")
                    //priluxJsonObjet.getString("FOTO")
            ))
            objetoNum++
        }

        val adapter = RecyclerAdapterNormativa(this, list)
        priluxCalcList.adapter = adapter

    }


}



