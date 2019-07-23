package com.sibela.bottomnavigationfab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_name.view.*

class StringAdapter(private val callbacks: Callbacks) :
    RecyclerView.Adapter<StringAdapter.ViewHolder>() {

    var names: List<String> = arrayListOf()
        set(it) {
            field = it
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_name, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = names[position]
        holder.nameText.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
        holder.nameText.text = name
        holder.itemView.setOnClickListener { callbacks.onNameSelected(name) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.name
    }

    interface Callbacks {
        fun onNameSelected(name: String)
    }
}