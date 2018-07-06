package com.grupoprilux.priluxcalc.Luminarias


/*
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.VistaPepe> ()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaPepe {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.card_layout_luminarias, parent, false)
        return VistaPepe(view)
    }

    override fun onBindViewHolder(holder: VistaPepe, position: Int) {
        holder!!.itemTitle.text = luminarias[position].nombre
        holder!!.itemSubtitle.text = luminarias[position].lumenes.toString()
        //holder!!.itemImage.setImageResource(images[position])
    }


    private var luminarias = arrayOf(Luminaria("Bura",10000.0,120.0),
            Luminaria("Hexagon",12000.0,90.0),
            Luminaria("Niza",4000.0,120.0),
            Luminaria("Nigra",4000.0,120.0),
            Luminaria("Roma",4000.0,120.0),
            Luminaria("Berlin",4000.0,120.0),
            Luminaria("Denver",4000.0,120.0),
            Luminaria("Rio",4000.0,120.0))

    private val subTitles = arrayOf("Subtítulo 001", "Subtítulo 002", "Subtítulo 003", "Subtítulo 004", "Subtítulo 005", "Subtítulo 006"
            , "Subtítulo 007", "Subtítulo 008")

      private val images = arrayOf(R.drawable.android_image_1
              , R.drawable.android_image_2
              , R.drawable.android_image_3
              , R.drawable.android_image_4
              , R.drawable.android_image_5
              , R.drawable.android_image_6
              , R.drawable.android_image_7
              , R.drawable.android_image_8)




    override fun getItemCount(): Int {
        return luminarias.count()
    }




    inner class VistaPepe(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemSubtitle: TextView


        init {
            itemView.setOnClickListener { v: View  ->
                var posicion: Int = adapterPosition

                Snackbar.make(itemView, "Has seleccionado la celda $posicion", Snackbar.LENGTH_LONG).setAction("Accion", null).show()

                var intent = Intent(v.context, CardNormativa::class.java)


                //Con esto mandamos informacion primitiva
                intent.putExtra("NOMBRELUMINARIA",luminarias[posicion].nombre)
                intent.putExtra("LUMENESLUMINARIA",luminarias[posicion].lumenes)
                intent.putExtra("APERTURALUMINARIA",luminarias[posicion].apertura)

                //Esto envia la informacion siempre es lo ultimo que se hace
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
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.grupoprilux.priluxcalc.Normativas.CardNormativa
import com.grupoprilux.priluxcalc.R
import com.squareup.picasso.Picasso


class RecyclerAdapter(val context: Context, val list: ArrayList<Luminaria>): BaseAdapter() {

    override fun getView(pos: Int, itemView: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.card_layout_luminarias, p2, false)
        val priluxNombre = view.findViewById<TextView>(R.id.item_detail) //as TextView
        val priluxCodigo = view.findViewById<TextView>(R.id.item_title)
        val fotoImage : ImageView = view.findViewById(R.id.item_image)



            view.setOnClickListener { v: View ->
                var posicion: Int = pos
                Snackbar.make(view, "Has seleccionado la celda $posicion", Snackbar.LENGTH_LONG).setAction("Accion", null).show()

                var intent = Intent(v.context, CardNormativa::class.java)


                //Con esto mandamos informacion primitiva
                intent.putExtra("NOMBRELUMINARIA", list[posicion].nombre)
                intent.putExtra("LUMENESLUMINARIA", list[posicion].lumenes)
                intent.putExtra("APERTURALUMINARIA", list[posicion].apertura)

                //Esto envia la informacion siempre es lo ultimo que se hace
                view.context.startActivity(intent)
            }


        priluxNombre.text = list[pos].nombre.toString()
        priluxCodigo.text = list[pos].codigo.toString()
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
}