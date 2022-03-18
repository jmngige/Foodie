package com.starsolns.emenu.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starsolns.emenu.R
import com.starsolns.emenu.data.database.Recipe
import com.starsolns.emenu.databinding.RecipeItemLayoutBinding

class RecipeListAdapter(
    private var context: Context
): RecyclerView.Adapter<RecipeListAdapter.ViewHolder>(){

    private var recipeList : List<Recipe> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecipeItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRecipe = recipeList[position]

        holder.title.text = currentRecipe.name
        Glide.with(context)
            .load(currentRecipe.image)
            .into(holder.image)

    }

    override fun getItemCount(): Int {
       return recipeList.size
    }

     inner class ViewHolder(view: RecipeItemLayoutBinding): RecyclerView.ViewHolder(view.root){

         val image = view.recipeItemImage
         val title = view.recipeItemTitle


        fun bind(mTitle: String, mImage: String){
            title.text = mTitle
            Glide.with(context).load(mImage).into(image)
        }
    }

    fun setData(dataList: List<Recipe>){
        recipeList = dataList
        notifyDataSetChanged()
    }
}