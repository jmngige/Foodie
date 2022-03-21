package com.starsolns.emenu.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.starsolns.emenu.R
import com.starsolns.emenu.databinding.FragmentMenuDetailsBinding
import com.starsolns.emenu.view.MainActivity

class MenuDetailsFragment : Fragment() {

    private var _binding: FragmentMenuDetailsBinding? = null
    private val binding get() = _binding!!

    private val args : MenuDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuDetailsBinding.inflate(layoutInflater, container, false)

        val recipe = args.currentRecipe

        binding.recipeDetailName.text = recipe.name
        Glide.with(requireContext())
            .load(recipe.image)
            .listener(object: RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                Palette.from(resource!!.toBitmap())
                    .generate(){
                        val intColor = it?.vibrantSwatch?.rgb ?: 0
                        binding.recipeDetailsLayout.setBackgroundColor(intColor)
                    }

                    return false
                }

            })
            .into(binding.recipeDetailImage)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}