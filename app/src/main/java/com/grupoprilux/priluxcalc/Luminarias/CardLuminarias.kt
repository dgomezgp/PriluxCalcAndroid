

package com.grupoprilux.priluxcalc.Luminarias

/*
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.os.AsyncTask
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.android.synthetic.main.activity_card_luminarias.*
import kotlinx.android.synthetic.main.content_main_luminarias.*
import kotlin.collections.ArrayList


class CardLuminarias : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.VistaPepe>? = null




    override fun onCreate(savedInstanceState: Bundle?) {

        //var objeto1 = Luminaria("Bombilla", 20.0, 10.0)

        val url = "http://www.grupoprilux.com/priluxcalc/test.php"
        AsyncTaskHandleJSON().execute(url)


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

        //{"TIPO":"X","CODIGO":"PRIL00000535670","CORTO":"535670","NOMBRE":"BURA ELITE 50W 90\u00ba 750 DALI ",
        // "LUMENES":"5878","APERTURA":"90","FOTO":"http:\/\/www.grupoprilux.com\/productos\/01\/tecnico\/tecnico\/bura-elite\/prilux-bura-elite-50w.png"},

        val jsonArray = JSONArray(jsonString)
        var list = ArrayList<Luminaria>()
        var objetoNum = 0

        while(objetoNum < jsonArray.length()) {
            val priluxJsonObjet = jsonArray.getJSONObject(objetoNum)
            list.add(Luminaria(
                    priluxJsonObjet.getString("TIPO"),
                    priluxJsonObjet.getString("CODIGO"),
                    priluxJsonObjet.getString("CORTO"),
                    priluxJsonObjet.getString("NOMBRE"),
                    priluxJsonObjet.getString("LUMENES"),
                    priluxJsonObjet.getString("APERTURA"),
                    priluxJsonObjet.getString("FOTO")
            ))
            objetoNum++
        }
        //val adapter = ListAdapter(this, list)
        //recycler_view.adapter = adapter
    }

}
*/

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.grupoprilux.priluxcalc.R
import kotlinx.android.synthetic.main.activity_card_luminarias.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class CardLuminarias : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_luminarias)


        val url = "http://www.grupoprilux.com/priluxcalc/test.php"
        AsyncTaskHandleJSON().execute(url)
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

        //{"TIPO":"X","CODIGO":"PRIL00000535670","CORTO":"535670","NOMBRE":"BURA ELITE 50W 90\u00ba 750 DALI ",
        // "LUMENES":"5878","APERTURA":"90","FOTO":"http:\/\/www.grupoprilux.com\/productos\/01\/tecnico\/tecnico\/bura-elite\/prilux-bura-elite-50w.png"},

        val jsonArray = JSONArray(jsonString)
        var list = ArrayList<Luminaria>()
        var objetoNum = 0

        while(objetoNum < jsonArray.length()) {
            val priluxJsonObjet = jsonArray.getJSONObject(objetoNum)
            list.add(Luminaria(
                    priluxJsonObjet.getString("TIPO"),
                    priluxJsonObjet.getString("CODIGO"),
                    priluxJsonObjet.getString("CORTO"),
                    priluxJsonObjet.getString("NOMBRE"),
                    priluxJsonObjet.getString("LUMENES"),
                    priluxJsonObjet.getString("APERTURA"),
                    priluxJsonObjet.getString("FOTO")
            ))
            objetoNum++
        }

        val adapter = RecyclerAdapter(this, list)
        priluxCalcList.adapter = adapter

    }




}


