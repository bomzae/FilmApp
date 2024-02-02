package com.example.filmapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val items: List<Map<String, Any>>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_people, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvRepRole: TextView = itemView.findViewById(R.id.tv_rep_role)
        val tvFilmoNames: TextView = itemView.findViewById(R.id.tv_filmo_names)

        fun setItem(item: Map<String, Any>) {
            Log.d("json Info.", item.toString())
            tvName.text = item["peopleNm"].toString() + "(" + item["peopleNmEn"].toString() + ")"
            tvRepRole.text = item["repRoleNm"].toString()
            tvFilmoNames.text = item["filmoNames"].toString()
        }
    }
}