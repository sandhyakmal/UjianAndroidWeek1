package com.example.ujianandroidweek1

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_check_in.*
import kotlinx.android.synthetic.main.activity_izin.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class IzinActivity : AppCompatActivity() {

    var dt: Boolean = false
    var tt: Boolean = false

    var foto1: Boolean = false
    var foto2: Boolean = false
    var foto3: Boolean = false

    var fda = Calendar.getInstance().getTime()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_izin)

        btnDariTanggal.setOnClickListener(View.OnClickListener {
            dt = true
            pickDate(it)
        })

        btnSampaiTanggal.setOnClickListener(View.OnClickListener {
            tt = true
            pickDate(it)
        })

        imageView.setOnClickListener() {
            foto1 = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, CheckInActivity.REQUEST_CODE_PERMISIONS)
                } else {
                    captureCamera()
                }
            }
        }

        imageView2.setOnClickListener() {
            foto2 = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, CheckInActivity.REQUEST_CODE_PERMISIONS)
                } else {
                    captureCamera()
                }
            }
        }
        imageView3.setOnClickListener() {
            foto3 = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, CheckInActivity.REQUEST_CODE_PERMISIONS)
                } else {
                    captureCamera()
                }
            }
        }

        btnKirim.setOnClickListener(View.OnClickListener {
            checkValid(it)
        })

    }

    fun captureCamera() {
        val takeCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(takeCamera, CheckInActivity.CAMERA_REQUEST)
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
            CheckInActivity.REQUEST_CODE_PERMISIONS -> {
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
        if(requestCode == CheckInActivity.CAMERA_REQUEST && resultCode == RESULT_OK){
            if (foto1 == true){
                val bitmapImage = data?.extras?.get("data") as Bitmap
                imageView.setImageBitmap(bitmapImage)
                foto1 = false
            } else if (foto2 == true){
                val bitmapImage = data?.extras?.get("data") as Bitmap
                imageView2.setImageBitmap(bitmapImage)
                foto2 = false
            } else if (foto3 == true){
                val bitmapImage = data?.extras?.get("data") as Bitmap
                imageView3.setImageBitmap(bitmapImage)
                foto3 = false
            }
//            val bitmapImage = data?.extras?.get("data") as Bitmap
//            saveImage(bitmapImage)
//            imageView.setImageBitmap(bitmapImage)
//            Toast.makeText(this, "Berhasil Ambil Foto", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Gagal Ambil Foto Lampiran", Toast.LENGTH_LONG).show()

        }
    }

    fun pickDate(view: View){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dateSetListener = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
                c.set(Calendar.YEAR,year)
                c.set(Calendar.MONTH,month)
                c.set(Calendar.DAY_OF_MONTH,day)

                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
//                txtDariTanggal!!.setText(sdf.format(c.getTime()))
                if(dt == true){
                    fda = c.getTime()
                    txtDariTanggal.setText(sdf.format(c.getTime()))
                    dt = false
                }
                else if(tt == true){
                    val tda = c.getTime()
                    if (tda.compareTo(fda) < 0){
                        val fdas = fda
                        Toast.makeText(applicationContext, "Pilih tanggal yang lebih besar dari $fda", Toast.LENGTH_SHORT).show()
                    }
                    else if (tda.compareTo(fda) > 0)
                    {
                        txtSampaiTanggal.setText(sdf.format(c.getTime()))
                    }
                    tt = false}

            }

        }
        DatePickerDialog(this, dateSetListener,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


    fun checkValid(view: View){
        if (txtDariTanggal.text.toString().contentEquals("") || txtSampaiTanggal.text.toString().contentEquals("") || txtPerihal.text.toString().contentEquals("") || txtKeterangan.text.toString().contentEquals("")){
            Toast.makeText(this, "Data tidak boleh Kosong", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Data berhasil di simpan", Toast.LENGTH_LONG).show()
        }
    }


}


