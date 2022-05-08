package com.boreal.puertocorazon.core.component.bottomsheet

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieDrawable
import com.boreal.commonutils.extensions.invisibleView
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.core.R
import com.boreal.puertocorazon.core.databinding.ABottomSheetOptionsImageFragmentBinding
import com.boreal.puertocorazon.core.utils.getImageBitmap
import com.boreal.puertocorazon.core.utils.getImageUri
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ABottomSheetOptionsImageFragment(
    private var imageReturn: (Uri) -> Unit
) : BottomSheetDialogFragment() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_GALLERY_CAPTURE = 2
    private var mImageSelectedUri: Uri? = null
    private val permissionCamera = android.Manifest.permission.CAMERA
    private val permissionStorage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    private val permissionReadStorage = android.Manifest.permission.READ_EXTERNAL_STORAGE

    lateinit var mBinding: ABottomSheetOptionsImageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ABottomSheetOptionsImageFragmentBinding.inflate(layoutInflater)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initElements()
    }

    fun goToImageUri() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CAPTURE)
        showLoading()
    }

    private fun showLoading() {
        mBinding.apply {
            loadingImage.setAnimation(R.raw.last_loading)
            loadingImage.repeatCount = LottieDrawable.INFINITE
            loadingImage.playAnimation()
            title.text = "Espere un momento, por favor..."
            loadingImage.showView()
            roundableGalery.invisibleView()
            roundableCamera.invisibleView()
            lblCamera.invisibleView()
            lblGallery.invisibleView()
        }
    }

    private fun goToCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            activity?.let {
                takePictureIntent.resolveActivity(it.packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
        showLoading()
    }

    fun getPermissionsStorage() {
        val providerContext =
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                permissionStorage
            )
        if (providerContext) {
            requestAllPermissions(REQUEST_GALLERY_CAPTURE)
        } else {
            requestAllPermissions(REQUEST_GALLERY_CAPTURE)
        }
    }

    fun getPermissionsCamera() {
        val providerContext =
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permissionCamera)

        if (providerContext) {
            requestAllPermissions(REQUEST_IMAGE_CAPTURE)
        } else {
            requestAllPermissions(REQUEST_IMAGE_CAPTURE)
        }
    }

    //TODO Buscar el metodo no deprecado
    private fun requestAllPermissions(requestCode: Int) {
        requestPermissions(
            arrayOf(permissionCamera, permissionReadStorage, permissionStorage),
            requestCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    goToCamera()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "No cuenta con permisos de camara",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            REQUEST_GALLERY_CAPTURE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    goToImageUri()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "No cuenta con permisos de almacenamiento",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_GALLERY_CAPTURE -> {
                    mImageSelectedUri = data?.data
                    isCancelable = false
                    lifecycleScope.launch {
                        imageReturn.invoke(withContext(Dispatchers.IO) {
                            mImageSelectedUri!!.getImageBitmap().getImageUri()
                        })
                        dismiss()
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val cameraBitmap = data?.extras?.get("data") as Bitmap
                    isCancelable = false
                    lifecycleScope.launch(Dispatchers.Main) {
                        imageReturn.invoke(withContext(Dispatchers.IO) {
                            cameraBitmap.getImageUri()
                        })
                        dismiss()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "No se capturó la imagen", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

}