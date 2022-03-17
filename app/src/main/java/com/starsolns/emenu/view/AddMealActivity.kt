package com.starsolns.emenu.view

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.starsolns.emenu.R
import com.starsolns.emenu.databinding.ActivityAddMealBinding
import com.starsolns.emenu.databinding.CustomAddImageLayoutBinding
import com.starsolns.emenu.databinding.CustomListLayoutBinding
import com.starsolns.emenu.ui.adapter.CustomListAdapter
import com.starsolns.emenu.util.Constants
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMealBinding

    private var imagePath: String = ""

    private lateinit var customListDialog :Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.addMealImage.setOnClickListener {
            loadCustomAddImageOptions()
        }

        binding.addMealType.setOnClickListener {
            loadCustomListOptions("Select Meal Type", Constants.getMealTypes(), Constants.MEAL_TYPE)
        }

        binding.addMealCategory.setOnClickListener {
            loadCustomListOptions("Select Meal Category", Constants.getMealCategories(), Constants.MEAL_CATEGORY)
        }

        binding.addMealDuration.setOnClickListener {
            loadCustomListOptions("Select preparation Duration", Constants.getMealDuration(), Constants.MEAL_DURATION)
        }

    }

    private fun loadCustomListOptions(title: String, itemsList: List<String>, selectedItem: String){
        val customBinding: CustomListLayoutBinding = CustomListLayoutBinding.inflate(layoutInflater)
        customListDialog = Dialog(this)
        customListDialog.setContentView(customBinding.root)
        customBinding.mealSelectionTitle.text = title

        val adapter = CustomListAdapter(this, itemsList, selectedItem)
        customBinding.itemsListRv.layoutManager = LinearLayoutManager(this)
        customBinding.itemsListRv.adapter = adapter

        customListDialog.show()

    }

     fun setSelectedItem(item: String, selection: String){
        when(selection){
            Constants.MEAL_TYPE -> {
                binding.addMealType.setText(item)
                customListDialog.dismiss()
            }
            Constants.MEAL_CATEGORY ->{
                binding.addMealCategory.setText(item)
                customListDialog.dismiss()
            }
            Constants.MEAL_DURATION ->{
                binding.addMealDuration.setText(item)
                customListDialog.dismiss()
            }
        }
    }


    private fun loadCustomAddImageOptions() {
        val customBinding = CustomAddImageLayoutBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(customBinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.show()

        customBinding.galleryOption.setOnClickListener {
            requestGalleryAccessPermissions(dialog)

        }

        customBinding.cameraOption.setOnClickListener {
            requestCameraAccessPermissions(dialog)
        }

    }

    private fun requestCameraAccessPermissions(dialog: Dialog) {
        Dexter.withContext(this).withPermissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).withListener(object: MultiplePermissionsListener{
            override fun onPermissionsChecked(permsReport: MultiplePermissionsReport?) {
                if(permsReport!!.areAllPermissionsGranted()){
                   val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_OPTION_REQUEST_CODE)
                    dialog.dismiss()
                }
            }
            override fun onPermissionRationaleShouldBeShown(
                permsRequest: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                showCustomDialogOnPermissionsDeny()
            }

        }).onSameThread().check()
    }

    private fun requestGalleryAccessPermissions(dialog: Dialog) {
        Dexter.withContext(this).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(permsReport: MultiplePermissionsReport?) {
                if (permsReport!!.areAllPermissionsGranted()) {
                   val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY_OPTION_REQUEST_CODE)
                    dialog.dismiss()
                }else{
                    showCustomDialogOnPermissionsDeny()
                }
            }
            override fun onPermissionRationaleShouldBeShown(
                permsRequest: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                showCustomDialogOnPermissionsDeny()
            }
        }).onSameThread().check()
    }

    private fun showCustomDialogOnPermissionsDeny() {
        AlertDialog.Builder(this)
            .setMessage("Some features won't work correctly without enabling these permissions")
            .setTitle("!Warning")
            .setPositiveButton("Settings"){_,_->
                try{
                    val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package",packageName,null)
                    settingsIntent.data = uri
                    startActivity(settingsIntent)
                }catch (e : ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel"){_,_->
            }.show()
    }

    //save images captured and images selected to phones internal storage
    private fun saveSelectedImagesToInternalStorage(bitmap: Bitmap): String{
        val wrapper = ContextWrapper(applicationContext)

        var file = wrapper.getDir(EMENU_IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "emenu.${UUID.randomUUID()}.jpg")

        try{
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        return file.absolutePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_OPTION_REQUEST_CODE){
                data?.let {
                    val capturedImage = data.extras!!.get("data") as Bitmap

                    Glide.with(this)
                        .load(capturedImage)
                        .centerCrop()
                        .into(binding.addMealImageView)

                    imagePath = saveSelectedImagesToInternalStorage(capturedImage)
                    Log.i(TAG, imagePath)
                    binding.addMealImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit))
                }
            }
            if(requestCode == GALLERY_OPTION_REQUEST_CODE){
                data?.let {
                    val selectedImage = data.data
                    Glide.with(this)
                        .load(selectedImage)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                                resource?.let {

                                    val bitmap: Bitmap = resource.toBitmap()
                                    imagePath = saveSelectedImagesToInternalStorage(bitmap)
                                    Log.i(TAG, imagePath)
                                }
                                return false
                            }

                        })
                        .into(binding.addMealImageView)

                    binding.addMealImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit))
                }
            }
        }
    }

    companion object {
        private const val GALLERY_OPTION_REQUEST_CODE = 100
        private const val CAMERA_OPTION_REQUEST_CODE = 101

        private const val EMENU_IMAGE_DIRECTORY = "eMenuImages"
        private const val  TAG = "ImagePath"
    }

}