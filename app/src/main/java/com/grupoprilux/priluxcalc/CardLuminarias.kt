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
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem


class CardLuminarias : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_luminarias)


        if (isNetDisponible() != true || isOnlineNet() != true) {
            showNormalAlert(this)
        } else {

            val url = "http://www.grupoprilux.com/priluxcalc/test.php"
            AsyncTaskHandleJSON().execute(url)
        }
    }


    //Infla el menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu)
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
        priluxCalcList.adapter = adapter

    }


}


