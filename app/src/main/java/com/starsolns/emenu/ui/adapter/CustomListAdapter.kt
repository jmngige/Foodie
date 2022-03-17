package com.starsolns.emenu.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starsolns.emenu.databinding.CustomItemLayoutBinding

class CustomListAdapter(
    private val context: Context,
    private val itemsList: List<String>,
    private val selection: String
): RecyclerView.Adapter<CustomListAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemsList[position]
        holder.listItem.text = currentItem
    }

    override fun getItemCount() = itemsList.size

    class ViewHolder(binding: CustomItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        val listItem = binding.mealItemSelect
    }

}