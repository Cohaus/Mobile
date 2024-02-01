package com.solution.gdsc.ui.home.camera

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentCameraBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val REQUEST_TAKE_PHOTO = 1
private const val DATE_YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

@AndroidEntryPoint
class CameraFragment : BaseFragment<FragmentCameraBinding>(R.layout.fragment_camera), ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var currentPhotoPath: String
    private lateinit var encodeImage: String
    override fun setLayout() {
        dispatchTakePictureIntent()
        setClickListener()

    }

    private fun setClickListener() {
        with(binding) {
            ibBackIcon.setOnClickListener {
                findNavController().navigateUp()
            }
            btnCameraImageSave.setOnClickListener {
                val action = CameraFragmentDirections.actionCameraToRecordSave(currentPhotoPath)
                findNavController().navigate(action)
            }
            btnCameraRequestRepair.setOnClickListener {
                val action = CameraFragmentDirections.actionCameraToHomeLocationSetting()
                findNavController().navigate(action)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.progressBarSafeGradeHome.setProgress(50f)
    }

    private fun dispatchTakePictureIntent() {
        // Check if the camera permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Continue with taking a picture
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }

                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireActivity(),
                            "com.solution.gdsc.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                    }
                }
            }
        } else {
            // Request camera permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted, dispatchTakePictureIntent can be called now
                    dispatchTakePictureIntent()
                } else {
                    // Permission denied, show a message or handle accordingly
                    Log.d("CameraPermission", "Camera permission denied")
                }
                return
            }
            else -> {
                // Handle other permissions if needed
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 사진 캡쳐 후 처리
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("data")) {
                val imageBitmap = data.extras!!.get("data") as Bitmap
                binding.ivCameraImage.setImageBitmap(imageBitmap)
            } else {
                setPic()
            }
        } else if (resultCode == RESULT_CANCELED) { // 사진을 찍지 않고 뒤로 가기 시 처리
            findNavController().navigateUp()
        }
    }

    private fun compressBitmap(bitmap: Bitmap, maxSizeInKB: Int): String? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        var quality = 100
        while (outputStream.toByteArray().size / 1024 > maxSizeInKB) {
            outputStream.reset()
            quality -= 10
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }

        val compressedImageFile = saveBitmapToFile(outputStream.toByteArray())
        outputStream.close()
        return compressedImageFile?.absolutePath
    }

    private fun saveBitmapToFile(byteArray: ByteArray): File? {
        return try {
            val timeStamp = SimpleDateFormat(DATE_YEAR_MONTH_DAY_PATTERN, Locale.KOREA)
            val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
            imageFile.writeBytes(byteArray)
            imageFile
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat(DATE_YEAR_MONTH_DAY_PATTERN, Locale.KOREA)
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        currentPhotoPath = imageFile.absolutePath
        return imageFile
    }

    private fun setPic() {
        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, options)
        val rotation = getRotationFromExif(currentPhotoPath)
        val rotatedBitmap = rotateBitmap(bitmap, rotation)

        // 이미지를 압축하고 크기 조절
        val compressedImagePath = compressBitmap(rotatedBitmap, 200) // 200KB로 압축
        if (compressedImagePath != null) {
            currentPhotoPath = compressedImagePath
            binding.ivCameraImage.setImageBitmap(rotatedBitmap) // 또는 압축된 이미지를 표시하려면 이 부분을 수정하세요.
            encodeImage = encodeBitmapToBase64(rotatedBitmap)

            // 갤러리에 사진 추가
            galleryAddPic()
        } else {
            // 압축 실패 시의 처리
            Log.e("CameraFragment", "Failed to compress image")
        }
    }

    private fun galleryAddPic() {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "JPEG_${SimpleDateFormat(
                DATE_YEAR_MONTH_DAY_PATTERN, Locale.KOREA).format(Date())}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val contentResolver = requireActivity().contentResolver
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                val imageBitmap = BitmapFactory.decodeFile(currentPhotoPath)
                val rotation = getRotationFromExif(currentPhotoPath)
                val rotatedBitmap = rotateBitmap(imageBitmap, rotation)
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
        }
    }

    private fun getRotationFromExif(imagePath: String): Int {
        var rotation = 0
        try {
            val exif = ExifInterface(imagePath)
            when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotation = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> rotation = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> rotation = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return rotation
    }

    private fun rotateBitmap(bitmap: Bitmap, rotation: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotation.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun encodeBitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()

        // 이미지를 JPEG 형식으로 80% 품질로 압축
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

        val bytes = outputStream.toByteArray()
        outputStream.close()

        // Base64.NO_WRAP 옵션을 사용하여 줄 바꿈 문자 제거
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 101
    }
}