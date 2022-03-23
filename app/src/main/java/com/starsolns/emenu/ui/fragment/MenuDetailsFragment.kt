package com.starsolns.emenu.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.starsolns.emenu.R
import com.starsolns.emenu.databinding.FragmentMenuDetailsBinding
import com.starsolns.emenu.util.Constants
import com.starsolns.emenu.view.AddMealActivity
import com.starsolns.emenu.viewmodel.RoomViewModel

class MenuDetailsFragment : Fragment() {

    private var _binding: FragmentMenuDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MenuDetailsFragmentArgs by navArgs()

    private lateinit var roomViewModel: RoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuDetailsBinding.inflate(layoutInflater, container, false)



        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipe = args.currentRecipe

        roomViewModel = ViewModelProvider(requireActivity())[RoomViewModel::class.java]

        binding.recipeDetailName.text = recipe.name

        Glide.with(requireContext())
            .load(recipe.image)
            .listener(object : RequestListener<Drawable> {
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
                        .generate {
                            val intColor = it?.vibrantSwatch?.rgb ?: 0
                            binding.recipeDetailsLayout.setBackgroundColor(intColor)
                        }

                    return false
                }

            })
            .into(binding.recipeDetailImage)

        if(args.currentRecipe.favourite){
            binding.menuFavouriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_selected
                )
            )
        }else{
            binding.menuFavouriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_unselected
                )
            )
        }

        binding.menuFavouriteButton.setOnClickListener {
            updateFavouriteOptions()
        }
    }

    private fun updateFavouriteOptions() {
        args.currentRecipe.favourite = !args.currentRecipe.favourite
        roomViewModel.updateRecipe(args.currentRecipe)

        if (args.currentRecipe.favourite) {
            binding.menuFavouriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_selected
                )
            )
        } else {
            binding.menuFavouriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_unselected
                )
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_details_appbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.editRecipe -> {
                val recipe = args.currentRecipe

                val editIntent = Intent(requireContext(), AddMealActivity::class.java)
                editIntent.putExtra(Constants.EXTRA_UPDATE_DETAILS, recipe)
                startActivity(editIntent)
            }
            R.id.deleteRecipe -> {
               deleteRecipe()
            }
            R.id.shareRecipe -> {
                val shareIntent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.type = "text/plain"
                    this.putExtra(Intent.EXTRA_SUBJECT, "recipe link")
                }

                startActivity(Intent.createChooser(shareIntent, "Share With..."))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteRecipe() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Delete ${args.currentRecipe.name}")
            .setMessage("Are you sure you want to delete ${args.currentRecipe.name}")
            .setIcon(R.drawable.ic_alert)
            .setPositiveButton("Delete"){_,_->
                val recipe = args.currentRecipe
                roomViewModel.deleteRecipe(recipe)
                val action = MenuDetailsFragmentDirections.actionMenuDetailsFragmentToAllMenuFragment()
                findNavController().navigate(action)
            }
            .setNegativeButton("Cancel"){_,_->}
        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}