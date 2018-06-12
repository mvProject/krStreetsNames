package com.mvp.krstreetsname

import android.support.v7.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

class StreetsAdapter(var streets: MutableList<Streets>, var context: Context) : RecyclerView.Adapter<StreetsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreetsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.streets_item,parent,false)
        return StreetsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return streets.size
    }

    override fun onBindViewHolder(holder: StreetsViewHolder, position: Int) {
        holder.bindNote(streets[position])
    }

    /**
     *
     */
    fun saveStreets(){
        StreetsStorage.writeData(context,streets)
        MsgUtils.writeLogAdapter("data saved")
    }

    /**
     *
     */
    fun loadStreets(){
        streets = StreetsStorage.readData(context)
        MsgUtils.writeLogAdapter("data loaded")
    }

    /**
     *
     */
    fun findStreets(str : String) : MutableList<Streets>{
        MsgUtils.writeLogAdapter("data filtered")
        return streets.filter {it.oldName.contains(str,true) or it.newName.contains(str,true)} as MutableList
    }
}