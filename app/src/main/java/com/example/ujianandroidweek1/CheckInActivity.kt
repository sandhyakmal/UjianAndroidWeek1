package com.example.ujianandroidweek1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_check_in.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class CheckInActivity : AppCompatActivity() {

    companion object {
        public val REQUEST_CODE_PERMISIONS = 999
        public val CAMERA_REQUEST = 998
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)

        btnImage.setOnClickListener() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, REQUEST_CODE_PERMISIONS)
                } else {
                    captureCamera()
                }
            }
        }
    }

    fun captureCamera() {
        val takeCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(takeCamera, CAMERA_REQUEST)
    }

    fun saveImage(bitmap: Bitmap) {
        val eksStorage  = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val tanggal     = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName    = eksStorage + "/BCAF_" + tanggal + ".png"

        var file: File? = null
        file = File(fileName)
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,0, bos)
        val bitmapData = bos.toByteArray()

        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    captureCamera()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_CANCELED){

            val intent = Intent(this,CheckInGagalActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Login Foto Selfie Gagal", Toast.LENGTH_LONG).show()
        } else {

            val bitmapImage = data?.extras?.get("data") as Bitmap
//            saveImage(bitmapImage)
            imgUser.setImageBitmap(bitmapImage)

            Toast.makeText(this, "Login Foto Selfie Berhasil", Toast.LENGTH_LONG).show()
            val intent = Intent(this,CheckInBerhasilActivity::class.java)
            intent.putExtra("image",bitmapImage);
            startActivity(intent)
        }
    }
}


