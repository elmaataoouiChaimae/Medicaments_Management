package com.example.medicaments_management.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.medicaments_management.Data.Medicament
import com.example.medicaments_management.databinding.ListItemBinding

class MedicamentAdapter(private var list_medicament:ArrayList<Medicament>):RecyclerView.Adapter<MedicamentAdapter.MedicamentHolder>(){
    var onItemClick:((Medicament)->Unit)?=null
    class MedicamentHolder(listView:ListItemBinding):RecyclerView.ViewHolder(listView.root) {
          var image:ImageView=listView.image
          var title:TextView=listView.title
          var prix:TextView=listView.price
          var adoze:TextView=listView.qte
          var description:TextView=listView.description
          var linear:LinearLayout=listView.linearLayout
          var hidden:RelativeLayout=listView.hidden
          var commander:Button=listView.commander
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentHolder {
        var itemView=ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        var holder=MedicamentHolder(itemView)
        return holder
    }

    override fun onBindViewHolder(holder: MedicamentHolder, position: Int) {
        var medicament=list_medicament[position]
        holder.image.setImageResource(medicament.image)
        holder.title.text=medicament.nom
        holder.prix.text=medicament.prix.toString()+"dh"
        holder.adoze.text=medicament.qte
        holder.description.text=medicament.description.toString()
        val isExpendale:Boolean=list_medicament[position].expendale
        holder.hidden.visibility=if(isExpendale) View.VISIBLE else View.GONE
        holder.linear.setOnClickListener{
            val medicaments=list_medicament[position]
            medicaments.expendale=!medicaments.expendale
            notifyItemChanged(position)
        }
        holder.commander.setOnClickListener{
            onItemClick?.invoke(medicament)
        }


    }

    override fun getItemCount(): Int {
        return list_medicament.size
    }
}