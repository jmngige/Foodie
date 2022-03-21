package com.starsolns.emenu.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.starsolns.emenu.R
import com.starsolns.emenu.data.database.Recipe
import com.starsolns.emenu.databinding.FragmentFavouriteBinding
import com.starsolns.emenu.ui.adapter.RecipeListAdapter
import com.starsolns.emenu.view.MainActivity
import com.starsolns.emenu.viewmodel.RoomViewModel

class FavouriteFragment : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var roomViewModel: RoomViewModel
    private lateinit var listAdapter: RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomViewModel = ViewModelProvider(requireActivity())[RoomViewModel::class.java]

        roomViewModel.getAllFavouriteRecipes.observe(viewLifecycleOwner){favourites->
            listAdapter.setData(favourites)
        }
        listAdapter = RecipeListAdapter(requireActivity(), object : RecipeListAdapter.OnItemClickListener {
            override fun onCLickListener(recipe: Recipe) {
                val action = FavouriteFragmentDirections.actionFavouriteFragmentToMenuDetailsFragment(recipe)
                findNavController().navigate(action)
                if (requireActivity() is MainActivity){
                    (activity as MainActivity?)?.hideBottomNavView()
                }
            }

        })

        binding.favouriteRecipeList.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.favouriteRecipeList.adapter = listAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavView()
        }
    }
}