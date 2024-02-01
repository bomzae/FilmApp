package com.example.filmapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class CollectionActivity : AppCompatActivity() {
    lateinit var saveBtn: Button // 리뷰 저장 버튼
    var DB:DBHelper?=null

    private lateinit var editTextDate: EditText
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var selectedImageUri: Uri
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        DB = DBHelper(this)
        saveBtn = findViewById(R.id.btn_save)
        saveBtn.setOnClickListener {// DB에 저장 후 메인 페이지로 이동

            Toast.makeText(
                this@CollectionActivity,
                "등록되었습니다.",
                Toast.LENGTH_SHORT
            ).show()

            val loginIntent = Intent(this@CollectionActivity, HomeActivity::class.java)
            startActivity(loginIntent)
        }

        editTextDate = findViewById(R.id.edt_date)
        editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    // 날짜를 선택한 후의 동작을 여기에 구현
                    val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                    editTextDate.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        imageView = findViewById(R.id.img_selected_photo)

        val btnUpload: Button = findViewById(R.id.btn_upload)
        btnUpload.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data!!
            imageView.setImageURI(selectedImageUri)
            imageView.visibility = View.VISIBLE
        }
    }
}