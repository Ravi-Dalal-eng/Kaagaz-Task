package com.messaging.kaagaztask.activities

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.messaging.kaagaztask.database.OurDatabase
import com.messaging.kaagaztask.databinding.ActivityCameraBinding
import com.messaging.kaagaztask.entities.Album
import com.messaging.kaagaztask.entities.Photo
import com.messaging.kaagaztask.utils.OurRepository
import com.messaging.kaagaztask.utils.OurViewModel
import com.messaging.kaagaztask.utils.OurViewModelFactory
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var imageCapture:ImageCapture
    private lateinit var albumName:String
    private lateinit var ourViewModel: OurViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

       initialize()

        binding.clickPhoto.setOnClickListener {
            takePhoto()
        }
        binding.latestClickedPhotos.setOnClickListener {
            val intent=Intent(this,PreviewActivity::class.java)
            intent.putExtra("albumName",albumName)
            startActivity(intent)
        }
    }

    private fun initialize() {
        albumName= SimpleDateFormat(ALBUM_NAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val ourDatabase= OurDatabase.getDatabase(applicationContext)
        val ourRepository= OurRepository(ourDatabase)
        ourViewModel= ViewModelProvider(this, OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoName = "JPEG_${System.currentTimeMillis()}"
        val file = File(externalMediaDirs[0],photoName)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults){

                    Toast.makeText(baseContext,"Photo captured successfully", Toast.LENGTH_SHORT).show()
                    ourViewModel.addAlbum(Album(0,albumName))
                    ourViewModel.addPhoto(Photo(0,albumName,photoName))
                    binding.latestClickedPhotos.visibility=View.VISIBLE
                    val latestPhotos = File(externalMediaDirs[0],photoName)
                    Glide.with(binding.root).load(latestPhotos).into(binding.latestClickedPhotos)
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
                .also {
                    it.setSurfaceProvider(binding.preview.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder()
                .build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview,imageCapture)

            } catch(exc: Exception) {
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    companion object {
        private const val TAG = "CameraXApp"
        private const val ALBUM_NAME_FORMAT ="yyyy.MM.dd 'at' HH:mm:ss"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).toTypedArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Please grant the permissions",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}