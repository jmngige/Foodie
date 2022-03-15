package com.starsolns.emenu.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import com.starsolns.emenu.R
import com.starsolns.emenu.databinding.ActivityAddMealBinding
import com.starsolns.emenu.databinding.ActivityMainBinding
import com.starsolns.emenu.databinding.CustomAddImageLayoutBinding

class AddMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.addMealImage.setOnClickListener {
            loadCustomAddImageOptions()
        }

    }

    private fun loadCustomAddImageOptions() {
        val customBinding  = CustomAddImageLayoutBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(customBinding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()

        customBinding.galleryOption.setOnClickListener {
            Toast.makeText(this, "Choose from phone gallery", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

        customBinding.cameraOption.setOnClickListener {
            Toast.makeText(this, "Take image from phone camera", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

    }


}