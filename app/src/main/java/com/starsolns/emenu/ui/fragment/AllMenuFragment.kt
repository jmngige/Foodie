package com.starsolns.emenu.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.starsolns.emenu.R
import com.starsolns.emenu.data.database.Recipe
import com.starsolns.emenu.databinding.FragmentAllMenuBinding
import com.starsolns.emenu.ui.adapter.RecipeListAdapter
import com.starsolns.emenu.util.Constants
import com.starsolns.emenu.view.AddMealActivity
import com.starsolns.emenu.view.MainActivity
import com.starsolns.emenu.viewmodel.RoomViewModel

class AllMenuFragment : Fragment() {

    private var _binding: FragmentAllMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var roomViewModel: RoomViewModel
    private lateinit var recipeListAdapter: RecipeListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentAllMenuBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomViewModel = ViewModelProvider(requireActivity())[RoomViewModel::class.java]
        roomViewModel.getAllRecipes.observe(viewLifecycleOwner) { recipes->
            recipeListAdapter.setData(recipes)
        }

        recipeListAdapter = RecipeListAdapter(requireContext(), object: RecipeListAdapter.OnItemClickListener{
            override fun onCLickListener(recipe: Recipe) {
                val action = AllMenuFragmentDirections.actionAllMenuFragmentToMenuDetailsFragment(recipe)
                findNavController().navigate(action)

                if(requireActivity() is MainActivity){
                    (activity as MainActivity?)?.hideBottomNavView()
                }
            }

        })
        binding.recipesListRv.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recipesListRv.adapter = recipeListAdapter

    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavView()
        }
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