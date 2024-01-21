package com.solution.gdsc.ui.home

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.navigation.fragment.findNavController
import com.solution.gdsc.R
import com.solution.gdsc.base.BaseFragment
import com.solution.gdsc.databinding.FragmentCameraBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val REQUEST_TAKE_PHOTO = 1
private const val DATE_YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

class CameraFragment : BaseFragment<FragmentCameraBinding>(R.layout.fragment_camera) {
    private lateinit var currentPhotoPath: String
    override fun setLayout() {
        dispatchTakePictureIntent()
        setClickListener()

    }

    private fun setClickListener() {
        with(binding) {
            btnImageReport.setOnClickListener {
                val action = CameraFragmentDirections.actionCameraToReportDialog()
                findNavController().navigate(action)
            }
            ibBackIcon.setOnClickListener {
                findNavController().navigateUp()
            }
            btnImageConfirm.setOnClickListener {
                findNavController().navigateUp()
            }
            btnImageSave.setOnClickListener {
                // 이미지 기록 화면 이동 구현
            }
        }
    }

    private fun dispatchTakePictureIntent() {
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
                Log.d("PicImage", photoFile.toString())
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
        options.inSampleSize = 1
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, options)
        val rotation = getRotationFromExif(currentPhotoPath)
        val rotatedBitmap = rotateBitmap(bitmap, rotation)

        binding.ivCameraImage.setImageBitmap(rotatedBitmap)

        // 갤러리에 사진 추가
        galleryAddPic()
    }

    private fun galleryAddPic() {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "JPEG_${SimpleDateFormat(DATE_YEAR_MONTH_DAY_PATTERN, Locale.KOREA).format(Date())}.jpg")
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
}