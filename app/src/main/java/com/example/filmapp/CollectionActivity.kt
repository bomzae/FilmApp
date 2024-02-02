package com.example.filmapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream
import java.util.Calendar

class CollectionActivity : AppCompatActivity() {
    lateinit var saveBtn: Button // 리뷰 저장 버튼
    var DB:DBHelper?=null

    private lateinit var edt_title: EditText
    private lateinit var edt_director: EditText
    private lateinit var spn_genre: Spinner
    private lateinit var edt_date: EditText
    private lateinit var ed_acter: EditText
    private lateinit var edt_summary: EditText

    private lateinit var editTextDate: EditText
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var selectedImageUri: Uri
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        edt_title = findViewById(R.id.edt_title)
        edt_director = findViewById(R.id.edt_director)
        spn_genre = findViewById(R.id.spn_genre)
        edt_date = findViewById(R.id.edt_date)
        ed_acter = findViewById(R.id.ed_acter)
        edt_summary = findViewById(R.id.edt_summary)

        DB = DBHelper(this)
        saveBtn = findViewById(R.id.btn_save)
        saveBtn.setOnClickListener {// DB에 저장 후 메인 페이지로 이동
            val insert = DB!!.insertCollection(edt_title.text.toString(), edt_director.text.toString(), spn_genre.selectedItem.toString(), edt_date.text.toString(), ed_acter.text.toString(), edt_summary.text.toString())
            if (insert == true) {
                Toast.makeText(
                    this@CollectionActivity,
                    "저장되었습니다.",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this@CollectionActivity,
                    "오류가 발생했습니다.",
                    Toast.LENGTH_SHORT).show()
            }

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

    // 이미지 저장 함수
    private fun getByteArrayFromDrawble(d: Drawable): ByteArray { // drawable을 비트맵 변환 후 바이트 배열로 변환
        var bitmap: Bitmap = d.toBitmap()
        val stream: ByteArrayOutputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val data: ByteArray = stream.toByteArray()

        return data
    }
}