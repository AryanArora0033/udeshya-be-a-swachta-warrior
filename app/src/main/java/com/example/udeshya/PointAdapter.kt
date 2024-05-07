package com.example.udeshya

import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PointAdapter(private val listpoint:List<PointModel>):RecyclerView.Adapter<PointAdapter.ViewHolder>() {
    class ViewHolder(val item: View):RecyclerView.ViewHolder(item) {
           val name=item.findViewById<TextView>(R.id.txt_name)
           val point=item.findViewById<TextView>(R.id.txt_points)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointAdapter.ViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val item =inflater.inflate(R.layout.item_leaderboad,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: PointAdapter.ViewHolder, position: Int) {
        val item=listpoint[position]
        holder.name.text=item.name
        holder.point.text=item.point.toString()
    }

    override fun getItemCount(): Int {
        return listpoint.size

    }

}