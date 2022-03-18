package com.starsolns.emenu.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.starsolns.emenu.R
import com.starsolns.emenu.data.database.Recipe
import com.starsolns.emenu.databinding.CustomItemLayoutBinding
import com.starsolns.emenu.databinding.FragmentHomeBinding
import com.starsolns.emenu.ui.adapter.RecipeListAdapter
import com.starsolns.emenu.view.AddMealActivity
import com.starsolns.emenu.viewmodel.RoomViewModel

class AllMenuFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var roomViewModel: RoomViewModel
    private lateinit var recipeListAdapter: RecipeListAdapter

    private var recipesList = mutableListOf<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        roomViewModel = ViewModelProvider(requireActivity())[RoomViewModel::class.java]
        roomViewModel.getAllRecipes.observe(requireActivity(), Observer { recipes->
            recipesList.addAll(recipes)
            recipeListAdapter.notifyDataSetChanged()
        })

        recipeListAdapter = RecipeListAdapter(requireActivity(), recipesList)
        binding.recipesListRv.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recipesListRv.adapter = recipeListAdapter


        roomViewModel.getAllRecipes

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu_options, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
     when(item.itemId){
         R.id.addMealNav -> {
             val addMealIntent = Intent(context, AddMealActivity::class.java)
             startActivity(addMealIntent)
         }
     }
        return super.onOptionsItemSelected(item)
    }
}