package com.grupoprilux.priluxcalc


import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_card_luminarias.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import android.net.ConnectivityManager
import android.widget.Toast
import android.content.Intent
import android.app.Activity
import android.app.SearchManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView


class CardLuminarias : AppCompatActivity(), SearchView.OnQueryTextListener {


    ///SearchBar

    var tbMainSearch: Toolbar? = null
    var lvToolbarSerch: ListView? = null
    var arrays = arrayOf<Luminaria>()
    var adapter: ArrayAdapter<Luminaria>? = null
    var listaCompleta: ArrayList<Luminaria>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_luminarias)


        if (isNetDisponible() != true || isOnlineNet() != true) {
            showNormalAlert(this)
        } else {

            val url = "http://www.grupoprilux.com/priluxcalc/test.php"
            AsyncTaskHandleJSON().execute(url)
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
        searchView.setQueryHint("Buscar luminaria...")
        searchView.setOnQueryTextListener(this)
        Log.d("TAG", "onCreateOptionsMenu: mSearchmenuItem->" + mSearchmenuItem.getActionView());
        return true
    }

    //Llama a la funcion de ayuda (showInfoAlert)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.add_phrase -> {
                showInfoAlert(this)
                return true
            }
            R.id.search_button -> {
                item.setVisible(false)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    //Para comprobar si la red esta habilitada:


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
        intent.setClass(actividad, CardLuminarias::class.java)
        //llamamos a la actividad
        actividad.startActivity(intent)
        //finalizamos la actividad actual
        actividad.finish()
    }

    fun showNormalAlert(view: CardLuminarias) {
        val dialog = AlertDialog.Builder(this).setTitle("Alerta").setMessage("NO HAY CONEXION A INTERNET")
                .setPositiveButton("OK", { dialog, i ->
                })
                .setNegativeButton("REINTENTAR", { dialog, i -> reiniciarActivity(this) })
        dialog.show()
    }

    //Llama a un alert para mostrar ayuda
    fun showInfoAlert(view: CardLuminarias) {
        val dialog = AlertDialog.Builder(this).setTitle("AYUDA").setMessage("Elige el producto con el que quieras realizar el calculo")
                .setPositiveButton("OK", { dialog, i ->
                })
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


        val jsonArray = JSONArray(jsonString)
        var list = ArrayList<Luminaria>()
        var objetoNum = 0

        while (objetoNum < jsonArray.length()) {
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
        listaCompleta = list
        priluxCalcList.adapter = adapter

    }

//Searchbar


    override fun onQueryTextSubmit(query:String):Boolean {
        Log.d("TAGBUSCADO", "onQueryTextSubmit: query->" + query)
        return true
    }
    override fun onQueryTextChange(newText:String):Boolean {

        var newText2 = newText.toUpperCase()
        val newlist = ArrayList<Luminaria>()
        for (name in this.listaCompleta!!) {
            val getName = name.nombre.toUpperCase()
            if (getName.contains(newText2)) {
                newlist.add(name)
            }
        }
        val adapter = RecyclerAdapter(this, newlist)
        priluxCalcList.adapter = adapter
        return true
    }


}


