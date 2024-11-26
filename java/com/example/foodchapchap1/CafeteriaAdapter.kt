package com.example.cafeteria

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CafeteriaAdapter(private val cafeteriaList: List<Cafeteria>) :
    RecyclerView.Adapter<CafeteriaAdapter.CafeteriaViewHolder>() {

    class CafeteriaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvCafeteriaName)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val tvOpeningHours: TextView = view.findViewById(R.id.tvOpeningHours)
        val tvClosingHours: TextView = view.findViewById(R.id.tvClosingHours)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cafeteria, parent, false)
        return CafeteriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CafeteriaViewHolder, position: Int) {
        val cafeteria = cafeteriaList[position]
        holder.tvName.text = cafeteria.locationName
        holder.tvAddress.text = "Address: ${cafeteria.address}"
        holder.tvOpeningHours.text = "Opens: ${cafeteria.openingHours}"
        holder.tvClosingHours.text = "Closes: ${cafeteria.closingHours}"
    }

    override fun getItemCount(): Int = cafeteriaList.size
}
