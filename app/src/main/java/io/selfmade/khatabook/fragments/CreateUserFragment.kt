package io.selfmade.khatabook.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.theartofdev.edmodo.cropper.CropImage
import io.selfmade.khatabook.BuildConfig
import io.selfmade.khatabook.R
import io.selfmade.khatabook.activities.MainActivity
import io.selfmade.khatabook.databinding.CreateUserFragmentBinding
import io.selfmade.khatabook.model.CreateUser
import io.selfmade.khatabook.utilities.Constance
import io.selfmade.khatabook.utilities.Logs
import io.selfmade.khatabook.viewmodel.DataViewModel
import io.selfmade.khatabook.viewmodel.ModelFactory
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class CreateUserFragment : BaseFragment() {

    private lateinit var binding: CreateUserFragmentBinding
    private lateinit var activity: MainActivity


    private lateinit var currentPhotoPath: String
    private lateinit var shareUri: Uri
    private var storeUserProfilePath: String = ""
    private lateinit var dataViewModel: DataViewModel

    val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var isDenied = 2
            permissions.entries.forEach {
                if (it.value == false) {
                    val showRationale1: Boolean = shouldShowRequestPermissionRationale(it.key)
                    if (!showRationale1) {
                        isDenied = 0
                    } else {
                        isDenied = 1
                    }
                }
                Logs.e("${it.key} = ${it.value}")
            }
        }


    val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    val file = File(currentPhotoPath)
                    shareUri = FileProvider.getUriForFile(
                        this@CreateUserFragment.requireContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        file
                    )
                    binding.imgUser.setImageURI(Uri.fromFile(File(currentPhotoPath)))
                    Logs.e("currentPhotoPath 1 ${currentPhotoPath}")

                } else {

                    CropImage.activity(Uri.parse(currentPhotoPath))
                        .start(activity, this@CreateUserFragment)

                    Logs.e("currentPhotoPath 2 ${currentPhotoPath}")
                    val image: Bitmap =
                        getBitmapFromContentResolver(Uri.parse(currentPhotoPath))
                    binding.imgUser.setImageBitmap(image)

                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = CreateUserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()
        dataViewModel =
            ViewModelProvider(
                this@CreateUserFragment,
                ModelFactory()
            ).get(DataViewModel::class.java)

        binding.imgUser.setOnClickListener {
            clickPhoto()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_checked) {
            isDone()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun isDone() {
        val userName = binding.tilUserName.editText!!.text.toString()
        val phoneNumber = binding.tilPhone.editText!!.text.toString()

        binding.tilUserName.error = ""
        binding.tilPhone.error = ""
        if (storeUserProfilePath == "") {
            Constance.showDialog(activity, "Select", "Add user profile")
        } else if (userName.length == 0) {
            binding.tilUserName.error = "Please enter user name"
        } else if (phoneNumber.length == 0) {
            binding.tilPhone.error = "Please enter phone number"
        } else {
            submit()
        }
    }

    private fun submit() {

        Toast.makeText(activity, "Created User", Toast.LENGTH_SHORT).show()
        val userName = binding.tilUserName.editText!!.text.toString()
        val phoneNumber = binding.tilPhone.editText!!.text.toString()

        val createUser = CreateUser(storeUserProfilePath, userName, phoneNumber)
        dataViewModel.createUser(activity, createUser)

        activity.getNavController().popBackStack()
    }


    fun clickPhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(activity.packageManager)?.also {
            // Create the File where the photo should go
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                Toast.makeText(activity, "${ex.message}", Toast.LENGTH_LONG).show()
                null
            }
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    activity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    it
                )

                val mimeType = "image/*"
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, Constance.getNewFileName())
                    put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        Constance.getImageDirectoryPath()
                    )
                }

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                } else {
                    val imageUri =
                        activity.contentResolver.insert(
                            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL),
                            values
                        )
                    if (imageUri != null) {
                        currentPhotoPath = imageUri.toString()
                        shareUri = imageUri
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                }
                startActivityForResult.launch(takePictureIntent)
            }
        }
    }

    fun checkPermissions() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )

    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            getAppSpecificAlbumStorageDir(activity, Environment.DIRECTORY_PICTURES, "KhataBook")
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun getAppSpecificAlbumStorageDir(
        context: Context,
        albumName: String,
        subAlbumName: String
    ): File? {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        val file = File(
            context.getExternalFilesDir(
                albumName
            ), subAlbumName
        )
        if (!file?.mkdirs()) {
            Log.e("fssfsf", "Directory not created")
        }
        return file
    }

    fun getBitmapFromContentResolver(shareUri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor =
            activity.contentResolver.openFileDescriptor(shareUri, "r")!!
        val fileDescriptor: FileDescriptor = parcelFileDescriptor.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        activity.menuInflater.inflate(R.menu.menu_create_user, menu)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Logs.e("onRequestPermissionsResult :: ");

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            clickPhoto()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            Logs.e("resultCode ${resultCode}")

            if (resultCode == AppCompatActivity.RESULT_OK) {
                val resultUri = result.uri
                savefile(resultUri!!)
                binding.imgUser.setImageBitmap(getBitmapFromContentResolver(resultUri))

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    fun savefile(sourceuri: Uri) {
        val sourceFilename: String = File(sourceuri.getPath()!!).absolutePath
        Logs.e("sourceFilename $sourceFilename")
        val destinationFilename =
            Environment.getExternalStorageDirectory().path + "/" + Constance.getImageDirectoryPath() + "/" + Constance.getNewFileName()

        var bis: BufferedInputStream? = null
        var bos: BufferedOutputStream? = null

        try {
            bis = BufferedInputStream(FileInputStream(sourceFilename))
            bos = BufferedOutputStream(FileOutputStream(destinationFilename, false))
            val buf = ByteArray(1024)
            bis.read(buf)
            do {
                bos.write(buf)
            } while (bis.read(buf) != -1)
        } catch (e: IOException) {
            e.printStackTrace()
            Logs.e("IOException ${e.message}")

        } finally {
            try {
                if (bis != null) bis.close()
                if (bos != null) bos.close()

                storeUserProfilePath = destinationFilename

            } catch (e: IOException) {
                e.printStackTrace()
                Logs.e("IOException ${e.message}")
            }
        }
    }


}