package com.grupoprilux.priluxcalc

import android.content.Intent
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class RecyclerAdapterNormativa: RecyclerView.Adapter<RecyclerAdapterNormativa.VistaPepe2>() {

    private val normativas = arrayOf("OFICINA", "ALMACEN", "SALON", "BIBLIOTECA", "CLASE", "COCINA"
            , "BAÑO", "WTF")

    private val subTitles = arrayOf("TEST 001", "TEST  002", "TEST  003", "TEST  004", "TEST  005", "TEST  006"
            , "TEST  007", "TEST  008")

    /*private val images = arrayOf(R.drawable.android_image_1
            , R.drawable.android_image_2
            , R.drawable.android_image_3
            , R.drawable.android_image_4
            , R.drawable.android_image_5
            , R.drawable.android_image_6
            , R.drawable.android_image_7
            , R.drawable.android_image_8)

    */
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VistaPepe2 {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.card_layout_normativa, parent, false)
        return VistaPepe2(view)
    }

    override fun onBindViewHolder(holder: VistaPepe2?, position: Int) {
        holder!!.itemTitle.text = normativas[position]
        holder!!.itemSubtitle.text = subTitles[position]
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

                Snackbar.make(itemView, "Has seleccionado la celda $posicion", Snackbar.LENGTH_LONG).setAction("Accion", null).show()
                var intent = Intent(itemView.context,Calculos::class.java)
                itemView.context.startActivity(intent)

            }
            itemView.setBackgroundColor(Color.argb(100,143,143,143))
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemSubtitle = itemView.findViewById(R.id.item_detail)
        }
    }

}