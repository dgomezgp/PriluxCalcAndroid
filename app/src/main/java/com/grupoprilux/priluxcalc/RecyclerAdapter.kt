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
import com.squareup.picasso.Picasso


class RecyclerAdapter(val context: Context, val list: ArrayList<Luminaria>) : BaseAdapter() {

    override fun getView(pos: Int, itemView: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.card_layout_luminarias, p2, false)
        val priluxNombre = view.findViewById<TextView>(R.id.item_detail) //as TextView
        val priluxCodigo = view.findViewById<TextView>(R.id.item_title)
        val fotoImage: ImageView = view.findViewById(R.id.item_image)



        view.setOnClickListener { v: View ->
            var posicion: Int = pos
            var intent = Intent(v.context, CardNormativa::class.java)


            //Con esto mandamos informacion primitiva
            intent.putExtra("NOMBRELUMINARIA", list[posicion].nombre)
            intent.putExtra("LUMENESLUMINARIA", list[posicion].lumenes)
            intent.putExtra("APERTURALUMINARIA", list[posicion].apertura)

            //Esto envia la informacion siempre es lo ultimo que se hace
            view.context.startActivity(intent)
        }


        priluxNombre.text = list[pos].nombre
        priluxCodigo.text = list[pos].codigo
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