

package com.grupoprilux.priluxcalc


/*
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class RecyclerAdapterNormativa: RecyclerView.Adapter<RecyclerAdapterNormativa.VistaPepe2>() {

    private val normativas = arrayOf(Normativa("Calculo Libre",0.0 ,0.0),
            Normativa("Oficina",500.0,0.0),
            Normativa("Almacen",200.0,0.75),
            Normativa("Despacho",300.0,1.0),
            Normativa("Nave",400.0,1.0),
            Normativa("Laboratorio",500.0,1.0),
            Normativa("Garaje",200.0,1.0),
            Normativa("Salon",300.0,0.75))

    private val subTitles = arrayOf("TEST 001", "TEST  002", "TEST  003", "TEST  004", "TEST  005", "TEST  006"
            , "TEST  007", "TEST  008")

    private val images = arrayOf(R.drawable.android_image_1
            , R.drawable.android_image_2
            , R.drawable.android_image_3
            , R.drawable.android_image_4
            , R.drawable.android_image_5
            , R.drawable.android_image_6
            , R.drawable.android_image_7
            , R.drawable.android_image_8)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaPepe2 {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.card_layout_normativa, parent, false)
        return VistaPepe2(view)
    }

    override fun onBindViewHolder(holder: VistaPepe2, position: Int) {
        holder!!.itemTitle.text = normativas[position].nombre
        holder!!.itemSubtitle.text = normativas[position].luxes.toString()
        //holder!!.itemImage.setImageResource(images[position])


    }

    override fun getItemCount(): Int {
        return normativas.count()
    }

    inner class VistaPepe2(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemSubtitle: TextView

        init {
            itemView.setOnClickListener {
                var posicion: Int = adapterPosition





                // guardamos los valores recogidos en variables del tipo de datos a recibir utilizando su clave






                Snackbar.make(itemView, "Has seleccionado la celda $posicion", Snackbar.LENGTH_LONG).setAction("Accion", null).show()
                var intent = Intent(itemView.context,Calculos::class.java)

                val extras = intent.extras

                var apertura = extras.getDouble("APERTURALUMINARIA")
                var lumenes = extras.getDouble("LUMENESLUMINARIA")

                intent.putExtra("NOMBRENORMATIVA",normativas[posicion].nombre)
                intent.putExtra("LUXESNORMATIVA",normativas[posicion].luxes)
                intent.putExtra("DISTANCIASUELONORMATIVA",normativas[posicion].distanciaSuelo)
                intent.putExtra("LUMENESLUMINARIA",lumenes)
                intent.putExtra("APERTURALUMINARIA",apertura)

                itemView.context.startActivity(intent)

            }


            itemView.setBackgroundColor(Color.argb(100,143,143,143))
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemSubtitle = itemView.findViewById(R.id.item_detail)
        }
    }

}

*/

import android.widget.BaseAdapter
import android.content.Context
import android.content.Intent
import android.support.constraint.R.id.parent
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class RecyclerAdapterNormativa(val context: Context, val list: ArrayList<Normativa>): BaseAdapter() {

    override fun getView(pos: Int, itemView: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.card_layout_normativa, p2, false)
        val priluxNombre = view.findViewById<TextView>(R.id.item_detail) //as TextView
        val priluxCodigo = view.findViewById<TextView>(R.id.item_title)



        view.setOnClickListener { v: View ->
            var posicion: Int = pos
            Snackbar.make(view, "Has seleccionado la celda $posicion", Snackbar.LENGTH_LONG).setAction("Accion", null).show()

            var intent = Intent(v.context, Calculos::class.java)


            //Con esto mandamos informacion primitiva
            intent.putExtra("DISTANCIASUELONORMATIVA", list[posicion].nombre)
            intent.putExtra("LUXESNORMATIVA", list[posicion].luxes)
            intent.putExtra("APERTURALUMINARIA", list[posicion].altSuelo)

            //Esto envia la informacion siempre es lo ultimo que se hace
            view.context.startActivity(intent)
        }


        priluxNombre.text = list[pos].nombre.toString()
        priluxCodigo.text = list[pos].luxes.toString()


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
}
