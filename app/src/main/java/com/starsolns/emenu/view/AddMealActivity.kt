package com.starsolns.emenu.view

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.starsolns.emenu.R
import com.starsolns.emenu.databinding.ActivityAddMealBinding
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
                    Toast.makeText(applicationContext, "Gallery Option Accepted ", Toast.LENGTH_LONG).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_OPTION_REQUEST_CODE){
                data?.let {
                    val capturedImage = data.extras!!.get("data") as Bitmap
                    binding.addMealImageView.setImageBitmap(capturedImage)
                    binding.addMealImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit))
                }
            }
        }
    }

    companion object {
        const val GALLERY_OPTION_REQUEST_CODE = 10
        const val CAMERA_OPTION_REQUEST_CODE = 101
    }

}