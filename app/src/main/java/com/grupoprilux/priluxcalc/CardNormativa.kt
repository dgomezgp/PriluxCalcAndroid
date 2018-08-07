package com.grupoprilux.priluxcalc


import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

import com.grupoprilux.priluxcalc.R
import kotlinx.android.synthetic.main.activity_card_luminarias.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class CardNormativa : AppCompatActivity(), SearchView.OnQueryTextListener {


    var nombreLuminaria: String = ""
    var lumenes: String = ""
    var apertura: String = ""

    ///SearchBar

    var tbMainSearch: Toolbar? = null
    var lvToolbarSerch: ListView? = null
    var arrays = arrayOf<Normativa>()
    var adapter: ArrayAdapter<Normativa>? = null
    var listaCompleta: ArrayList<Normativa>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_normativa)


        if (isNetDisponible() != true || isOnlineNet() != true) {
            showNormalAlert(this)
        } else {

            val url = "http:www.grupoprilux.com/priluxcalc/normativa.php"
            AsyncTaskHandleJSON().execute(url)

            val extras = intent.extras ?: return


            // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave
            lumenes = extras.getString("LUMENESLUMINARIA")
            nombreLuminaria = extras.getString("NOMBRELUMINARIA")
            apertura = extras.getString("APERTURALUMINARIA")

            setUpViews()

        }

    }

    fun setUpViews() {
        tbMainSearch = findViewById(R.id.tb_toolbarsearch) as Toolbar
        lvToolbarSerch = findViewById(R.id.priluxCalcList) as ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrays)
        lvToolbarSerch!!.setAdapter(adapter)
        setSupportActionBar(tbMainSearch)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    //Infla el menu y searchbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val mSearchmenuItem = menu.findItem(R.id.menu_toolbarsearch)
        val searchView = mSearchmenuItem.getActionView() as SearchView
        searchView.setQueryHint("Buscar normativa...")
        searchView.setOnQueryTextListener(this)
        Log.d("TAG", "onCreateOptionsMenu: mSearchmenuItem->" + mSearchmenuItem.getActionView());
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
    fun showInfoAlert(view: CardNormativa) {
        val dialog = AlertDialog.Builder(this).setTitle("AYUDA").setMessage("Elige la normativa con respecto al lugar donde va a estar situada la luminaria")
                .setPositiveButton("OK", { dialog, i ->
                })
        dialog.show()
    }

    private fun isNetDisponible(): Boolean {

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val actNetInfo = connectivityManager.activeNetworkInfo

        return actNetInfo != null && actNetInfo.isConnected
    }

    //Para comprobar si hay acceso a internet:

    fun isOnlineNet(): Boolean? {

        try {
            val p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es")

            val `val` = p.waitFor()
            return `val` == 0

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    //reinicia una Activity
    fun reiniciarActivity(actividad: Activity) {
        val intent = Intent()
        intent.setClass(actividad, CardNormativa::class.java)
        //llamamos a la actividad
        actividad.startActivity(intent)
        //finalizamos la actividad actual
        actividad.finish()
    }

    fun showNormalAlert(view: CardNormativa) {
        val dialog = AlertDialog.Builder(this).setTitle("Alerta").setMessage("NO HAY CONEXION A INTERNET")
                .setPositiveButton("OK", { dialog, i ->
                    Toast.makeText(applicationContext, "Hello Friends", Toast.LENGTH_LONG).show()
                })
                .setNegativeButton("REINTENTAR", { dialog, i -> reiniciarActivity(this) })
        dialog.show()
    }

    inner class AsyncTaskHandleJSON : AsyncTask<String, String, String>() {
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

        while (objetoNum < jsonArray.length()) {
            val priluxJsonObjet = jsonArray.getJSONObject(objetoNum)
            list.add(Normativa(
                    priluxJsonObjet.getString("NOMBRE"),
                    priluxJsonObjet.getString("LUXES"),
                    priluxJsonObjet.getString("ALTSUELO"),
                    priluxJsonObjet.getString("FOTO")
            ))
            objetoNum++
        }

        val adapter = RecyclerAdapterNormativa(this, list)
        listaCompleta = list
        priluxCalcList.adapter = adapter
        adapter.enviarDatos(lumenes, nombreLuminaria, apertura)


    }


    //Searchbar


    override fun onQueryTextSubmit(query:String):Boolean {
        Log.d("TAGBUSCADO", "onQueryTextSubmit: query->" + query)
        return true
    }
    override fun onQueryTextChange(newText:String):Boolean {

        var newText2 = newText.toUpperCase()
        val newlist = ArrayList<Normativa>()
        for (name in this.listaCompleta!!) {
            val getName = name.nombre.toUpperCase()
            if (getName.contains(newText2)) {
                newlist.add(name)

            }
        }
        val adapter = RecyclerAdapterNormativa(this, newlist)
        priluxCalcList.adapter = adapter
        return true
    }

}



