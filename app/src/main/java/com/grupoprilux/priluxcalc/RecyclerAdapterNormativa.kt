package com.grupoprilux.priluxcalc


import android.widget.BaseAdapter
import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.grupoprilux.priluxcalc.Calculos
import com.grupoprilux.priluxcalc.R
import com.squareup.picasso.Picasso


class RecyclerAdapterNormativa(val context: Context, val list: ArrayList<Normativa>) : BaseAdapter() {

    var lumenes = ""
    var nombreLuminaria = ""
    var apertura = ""

    override fun getView(pos: Int, itemView: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.card_layout_normativa, p2, false)
        val priluxNombre = view.findViewById<TextView>(R.id.item_detail) //as TextView
        val priluxCodigo = view.findViewById<TextView>(R.id.item_title)
        val fotoImage: ImageView = view.findViewById(R.id.item_image)


        view.setOnClickListener { v: View ->
            var posicion: Int = pos
            var intent = Intent(v.context, Eleccion::class.java)


            Log.i("TAGADAPTER", "$nombreLuminaria****$lumenes++++$apertura")

            //Con esto mandamos informacion primitiva
            intent.putExtra("NOMBRENORMATIVA", list[posicion].nombre)
            intent.putExtra("LUXESNORMATIVA", list[posicion].luxes)
            intent.putExtra("ALTURASUELONORMATIVA", list[posicion].altSuelo)
            intent.putExtra("LUMENESLUMINARIA", lumenes)
            intent.putExtra("APERTURALUMINARIA", apertura)
            intent.putExtra("NOMBRELUMINARIA", nombreLuminaria)


            //Esto envia la informacion siempre es lo ultimo que se hace
            view.context.startActivity(intent)
        }


        priluxNombre.text = list[pos].nombre
        priluxCodigo.text = list[pos].luxes
        Picasso.get().load(list[pos].foto).into(fotoImage)



        return view
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    /**
     * Envia nuevos datos al adaptador.
     */
    fun enviarDatos(lumenes: String, nombreLuminaria: String, apertura: String) {
        this.lumenes = lumenes
        this.nombreLuminaria = nombreLuminaria
        this.apertura = apertura


    }


}
