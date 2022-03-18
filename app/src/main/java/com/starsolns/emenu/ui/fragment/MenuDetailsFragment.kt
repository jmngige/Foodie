package com.starsolns.emenu.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.starsolns.emenu.R
import com.starsolns.emenu.databinding.FragmentMenuDetailsBinding

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
        Glide.with(requireContext()).load(recipe.image).into(binding.recipeDetailImage)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}