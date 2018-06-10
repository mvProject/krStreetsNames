package com.mvp.krstreetsname

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class StreetsViewHolder(view: View?) : RecyclerView.ViewHolder(view){
    val oldname = view?.findViewById(R.id.old_street_name) as TextView
    val newname = view?.findViewById(R.id.new_street_name) as TextView

    fun bindNote(str: Streets) {
        oldname.text = str.oldName
        newname.text = str.newName
    }
}