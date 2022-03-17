package com.starsolns.emenu.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.starsolns.emenu.R
import com.starsolns.emenu.databinding.CustomItemLayoutBinding
import com.starsolns.emenu.view.AddMealActivity

class CustomListAdapter(
    private val activity: Activity,
    private val itemsList: List<String>,
    private val selection: String
): RecyclerView.Adapter<CustomListAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CustomItemLayoutBinding = CustomItemLayoutBinding.inflate(LayoutInflater.from(activity), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemsList[position]
        holder.listItem.text = currentItem

        holder.itemView.setOnClickListener {
            if(activity is AddMealActivity){
                activity.setSelectedItem(currentItem, selection)
            }
        }

    }

    override fun getItemCount() = itemsList.size

    class ViewHolder(view: CustomItemLayoutBinding): RecyclerView.ViewHolder(view.root){
    val listItem = view.mealItemSelect
    }

}