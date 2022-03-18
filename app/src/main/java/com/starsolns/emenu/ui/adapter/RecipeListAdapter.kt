package com.starsolns.emenu.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starsolns.emenu.data.database.Recipe
import com.starsolns.emenu.databinding.RecipeItemLayoutBinding

class RecipeListAdapter(
    private var context: Context,
    private var recipeList: List<Recipe>
): RecyclerView.Adapter<RecipeListAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecipeItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.bind(currentRecipe)

        //        ============ Alternative ===========
        //holder.bind(currentRecipe.name, currentRecipe.image)
    }

    override fun getItemCount(): Int {
       return recipeList.size
    }

    inner class ViewHolder(view: RecipeItemLayoutBinding): RecyclerView.ViewHolder(view.root){

        private val image = view.recipeItemImage
        private val title = view.recipeItemTitle

        fun bind(recipe: Recipe){
            title.text = recipe.name
            Glide.with(context).load(recipe.image).into(image)
        }

//        ============ Alternative ===========
//        fun bind(mTitle: String, mImage: String){
//            title.text = mTitle
//            Glide.with(context).load(mImage).into(image)
//        }
    }
}